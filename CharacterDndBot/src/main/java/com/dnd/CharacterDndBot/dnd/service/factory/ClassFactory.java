package com.dnd.CharacterDndBot.dnd.service.factory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.dnd.CharacterDndBot.bot.model.act.Act;
import com.dnd.CharacterDndBot.bot.model.act.ReturnAct;
import com.dnd.CharacterDndBot.bot.model.act.SingleAct;
import com.dnd.CharacterDndBot.bot.model.act.actions.Action;
import com.dnd.CharacterDndBot.bot.model.user.User;
import com.dnd.CharacterDndBot.dnd.dto.CharacterDnd;
import com.dnd.CharacterDndBot.dnd.dto.ClassDnd;
import com.dnd.CharacterDndBot.dnd.dto.comands.InerComand;
import com.dnd.CharacterDndBot.dnd.dto.wrap.ClassDndWrapp;
import com.dnd.CharacterDndBot.dnd.service.Executor;
import com.dnd.CharacterDndBot.dnd.service.Location;
import com.dnd.CharacterDndBot.dnd.service.logic.lvl.LvlAddExperience;
import com.dnd.CharacterDndBot.dnd.service.logic.proficiencies.UpProficiency;
import com.dnd.CharacterDndBot.dnd.util.ArrayToOneColums;
import com.dnd.CharacterDndBot.repository.ClassDndWrappService;

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
	private ArrayToOneColums arrayToOneColums;
	
	@Override
	public Act executeFor(Action action, User user) {
		return ReturnAct.builder()
				.target(START_B)
				.act(SingleAct.builder()
						.name("CreateClass")
						.text("What is your class?")
						.action(Action.builder()
								.location(Location.CLASS_FACTORY)
								.buttons(arrayToOneColums.rebuild(classDndWrappService.findDistinctClassName().toArray(String[]::new)))
								.build())
						.build())
				.build();
	}
}

@Component
class ChooseArchetype implements Executor<Action> {

	@Autowired
	private ArrayToOneColums arrayToOneColums;
	@Autowired
	private ClassDndWrappService classDndWrappService;
	
	@Override
	public Act executeFor(Action action, User user) {
		action.setButtons(arrayToOneColums.rebuild(classDndWrappService.findDistinctArchetypeByClassName(action.getAnswers()[0]).toArray(String[]::new)));
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
		Pattern pat = Pattern.compile("[-]?[0-9]+(.[0-9]+)?");
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
			action.getAnswers()[2] = 1 + "";
			action.setButtons(new String[][] { { "Okey" } });
			return SingleAct.builder()
					.name("checkClassCondition")
					.text(lvl + "??? I see you're new here. Let's start with lvl 1.\nAre you satisfied with this option?/n"
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
		classIntegrator.integrate(user.getCharactersPool().getActual(), classDndWrapp, lvl);
		return statFactory.executeFor(Action.builder().build(), user);
	}
}

@Component
class ClassIntegrator {

	@Autowired
	private ScriptReader scriptReader;
	@Autowired
	private LvlAddExperience lvlAddExperience;
	@Autowired
	private UpProficiency upProficiency;

	public void integrate(CharacterDnd character, ClassDndWrapp clazz, int lvl) {
		
		lvlAddExperience.addExperience(character.getLvl(), character.getLvl().getExpPerLvl()[lvl - 1]);
		upProficiency.build(character);
		
		ClassDnd classDnd = new ClassDnd();
		classDnd.setClassName(clazz.getClassName());
		classDnd.setArchetype(clazz.getArchetype());
		classDnd.setDiceHp(clazz.getDiceHp());
		classDnd.setFirstHp(clazz.getFirstHp());
		classDnd.setLvl(lvl);
		
		character.getDndClass().add(classDnd);
		
		for (int i = 0; i <= lvl; i++) {
			for (InerComand comand : clazz.getGrowMap()[i]) {
				scriptReader.execute(character, comand);
			}
		}
	}
}
