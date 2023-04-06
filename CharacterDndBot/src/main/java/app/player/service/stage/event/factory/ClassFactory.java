package app.player.service.stage.event.factory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import app.dnd.model.actions.Action;
import app.dnd.model.comands.InerComand;
import app.dnd.model.hero.ClassDnd;
import app.dnd.model.wrap.ClassDndWrapp;
import app.dnd.service.data.ClassDndWrappService;
import app.dnd.service.logic.lvl.LvlAddExperience;
import app.dnd.service.logic.talants.prof.UpProficiency;
import app.dnd.util.ArrayToColumns;
import app.player.model.act.Act;
import app.player.model.act.ReturnAct;
import app.player.model.act.SingleAct;
import app.player.model.enums.Location;
import app.player.service.stage.Executor;
import app.player.service.stage.event.factory.comandreader.ClassComandReader;
import app.user.model.ActualHero;
import app.user.model.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ClassFactory implements Executor<Action> {

	@Autowired
	private StartCreateClass startCreateClass;
	@Autowired
	private ChooseArchetype chooseArchetype;
	@Autowired
	private ChooseClassLvl chooseClassLvl;
	@Autowired
	private CheckLvlCondition checkLvlCondition;
	@Autowired
	private FinishClass finishClass;

	@Override
	public Act executeFor(Action action, User user) {

		switch (action.condition()) {
		case 0:
			return startCreateClass.executeFor(action, user);
		case 1:
			return chooseArchetype.executeFor(action, user);
		case 2:
			return chooseClassLvl.executeFor(action, user);
		case 3:
			return checkLvlCondition.executeFor(action, user);
		case 4:
			return finishClass.executeFor(action, user);
		}
		log.error("ClassFactory: out of bounds condition");
		return null;
	}
}

@Component
class StartCreateClass implements Executor<Action> {

	@Autowired
	private ClassDndWrappService classDndWrappService;
	@Autowired
	private ArrayToColumns arrayToColumns;

	@Override
	public Act executeFor(Action action, User user) {
		return ReturnAct.builder()
				.target(START_B)
				.act(SingleAct.builder()
						.name("CreateClass")
						.text("What is your class?")
						.action(Action.builder()
								.location(Location.CLASS_FACTORY)
								.buttons((String[][]) arrayToColumns.rebuild(classDndWrappService.findDistinctClassName().toArray(String[]::new), 1, String.class))
								.build())
						.build())
				.build();
	}
}

@Component
class ChooseArchetype implements Executor<Action> {

	@Autowired
	private ArrayToColumns arrayToColumns;
	@Autowired
	private ClassDndWrappService classDndWrappService;

	@Override
	public Act executeFor(Action action, User user) {
		action.setButtons((String[][]) arrayToColumns.rebuild(classDndWrappService.findDistinctArchetypeByClassName(action.getAnswers()[0]).toArray(String[]::new),1, String.class));
		return SingleAct.builder()
				.name("ChooseClassArchtype")
				.text(action.getAnswers()[0] + ", realy? Which one?")
				.action(action)
				.build();
	}
}

@Component
class ChooseClassLvl implements Executor<Action> {

	@Override
	public Act executeFor(Action action, User user) {
		action.setMediator(true);
		return SingleAct.builder()
				.name("ChooseClassLvl")
				.text("What is your lvl?")
				.action(action)
				.build();
	}
}

@Component
class CheckLvlCondition implements Executor<Action> {

	@Autowired
	private ClassDndWrappService classDndWrappService;

	@Override
	public Act executeFor(Action action, User user) {
		int lvl = 0;
		Pattern pat = Pattern.compile("([0-9]{1,2})+?");
		Matcher matcher = pat.matcher(action.getAnswers()[2]);
		while (matcher.find()) {
			lvl = ((Integer) Integer.parseInt(matcher.group()));
		}

		if (0 < lvl && lvl <= 20) {
			action.setButtons(new String[][] { { "Yeah, right" } });
			return SingleAct.builder()
					.name("checkClassCondition")
					.text(classDndWrappService.findDistinctInformationByClassNameAndArchetype(action.getAnswers()[0], action.getAnswers()[1])
							+ "\nIf not, select another option above.")
					.action(action)
					.build();
		} else {
			action.getAnswers()[2] = ""+1;
			action.setButtons(new String[][] { { "Okay" } });
			return SingleAct.builder()
					.name("checkClassCondition")
					.text(lvl + "??? I see you're new here. Let's start with lvl 1.\nAre you satisfied with this option?\n"
							+ classDndWrappService.findDistinctInformationByClassNameAndArchetype(action.getAnswers()[0], action.getAnswers()[1])
							+ "\nIf not, select another option above.")
					.action(action)
					.build();
		}
	}
}

@Component
class FinishClass implements Executor<Action> {

	@Autowired
	private ClassDndWrappService classDndWrappService;
	@Autowired
	private StatFactory statFactory;
	@Autowired
	private ClassIntegrator classIntegrator;

	@Override
	public Act executeFor(Action action, User user) {
		String className = action.getAnswers()[0];
		String archetype = action.getAnswers()[1];
		int lvl = ((Integer) Integer.parseInt(action.getAnswers()[2]));
		ClassDndWrapp classDndWrapp = classDndWrappService.findByClassNameAndArchetype(className, archetype);
		classIntegrator.integrate(user, classDndWrapp, lvl);
		return statFactory.executeFor(Action.builder().build(), user);
	}
}

@Component
class ClassIntegrator {

	@Autowired
	private ClassComandReader classComandReader;
	@Autowired
	private LvlAddExperience lvlAddExperience;
	@Autowired
	private UpProficiency upProficiency;

	public void integrate(User user, ClassDndWrapp clazz, int lvl) {

		ActualHero actualHero = user.getActualHero();
		actualHero.getCharacter().getLvl().setLvl(lvl);
		actualHero.getCharacter().getLvl().setExperience(lvlAddExperience.getExpPerLvl()[lvl - 1]);
		upProficiency.build(actualHero.getCharacter());
		ClassDnd classDnd = new ClassDnd();
		classDnd.setClassName(clazz.getClassName());
		classDnd.setArchetype(clazz.getArchetype());
		classDnd.setDiceHp(clazz.getDiceHp());
		classDnd.setFirstHp(clazz.getFirstHp());
		classDnd.setLvl(lvl);
		actualHero.getCharacter().getDndClass().add(classDnd);
		for (int i = 0; i <= lvl; i++) {
			for (InerComand comand : clazz.getGrowMap()[i]) {
				classComandReader.execute(actualHero.getCharacter(), comand);
			}
		}
	}
}
