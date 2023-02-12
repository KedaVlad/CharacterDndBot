package com.dnd.CharacterDndBot.service.dndTable.dndService.factoryService;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dnd.CharacterDndBot.datacontrol.ClassDndService;
import com.dnd.CharacterDndBot.service.acts.Act;
import com.dnd.CharacterDndBot.service.acts.ReturnAct;
import com.dnd.CharacterDndBot.service.acts.SingleAct;
import com.dnd.CharacterDndBot.service.acts.actions.Action;
import com.dnd.CharacterDndBot.service.bot.user.User;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.CharacterDnd;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.ClassDnd;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.comands.InerComand;
import com.dnd.CharacterDndBot.service.dndTable.dndService.Executor;
import com.dnd.CharacterDndBot.service.dndTable.dndService.Location;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
	private ClassDndService classDndService;
	@Override
	public Act executeFor(Action action, User user) {
		return ReturnAct.builder()
				.target(START_B)
				.act(SingleAct.builder()
						.name("CreateClass")
						.text("What is your class?")
						.action(Action.builder()
								.location(Location.CLASS_FACTORY)
								.buttons(getClassArray())
								.build())
						.build())
				.build();
	}

	private String[][] getClassArray() {
		List<String> all = classDndService.findDistinctClassName();
		String[][] allClasses = new String[all.size()][1];
		for (int i = 0; i < all.size(); i++) {
			allClasses[i][0] = all.get(i);
		}
		return allClasses;
	}
}

@Component
class ChooseArchetype implements Executor<Action> {

	@Autowired
	private ClassDndService classDndService;
	@Override
	public Act executeFor(Action action, User user) {
		action.setButtons(getArchetypeArray(action.getAnswers()[0]));
		return SingleAct.builder()
				.name("ChooseClassArchtype")
				.text(action.getAnswers()[0] + ", realy? Which one?")
				.action(action)
				.build();
	}

	private String[][] getArchetypeArray(String className) {
		List<String> all = classDndService.findDistinctArchetypeByClassName(className);
		String[][] allArchetypes = new String[all.size()][1];
		for (int i = 0; i < all.size(); i++) {
			allArchetypes[i][0] = all.get(i);
		}
		return allArchetypes;
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
	private ClassInformator classInformator;
	
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
					.text(classInformator.getObgectInfo(action.getAnswers()[0], action.getAnswers()[1])
							+ "\nIf not, select another option above.")
					.action(action)
					.build();
		} else {
			action.getAnswers()[2] = 1 + "";
			action.setButtons(new String[][] { { "Okey" } });
			return SingleAct.builder()
					.name("checkClassCondition")
					.text(lvl + "??? I see you're new here. Let's start with lvl 1.\nAre you satisfied with this option?/n"
							+ classInformator.getObgectInfo(action.getAnswers()[0], action.getAnswers()[1])
							+ "\nIf not, select another option above.")
					.action(action)
					.build();
		}
	}
}

@Component
class FinishClass implements Executor<Action> {

	@Autowired
	private ClassDndService classDndService;
	@Autowired
	private StatFactory statFactory;
	@Autowired
	private ClassIntegrator classIntegrator;

	@Override
	public Act executeFor(Action action, User user) {
		String className = action.getAnswers()[0];
		String archetype = action.getAnswers()[1];
		int lvl = ((Integer) Integer.parseInt(action.getAnswers()[2]));
		ClassDnd classDnd = classDndService.findByClassNameAndArchetype(className, archetype).get(0);
		classIntegrator.integrate(user.getCharactersPool().getActual(), classDnd, lvl);
		return statFactory.executeFor(Action.builder().build(), user);
	}
}

@Component
class ClassIntegrator {

	@Autowired
	private ScriptReader scriptReader;
	@Autowired
	private ClassInformator classInformator;

	public void integrate(CharacterDnd character, ClassDnd clazz, int lvl) {
		clazz.setLvl(lvl);
		character.setDndClass(new ClassDnd[] { clazz });
		for (int i = 0; i < lvl; i++) {
			for (InerComand comand : clazz.getGrowMap()[i]) {
				scriptReader.execute(character, comand);
			}
		}
	}
}

@Component
class ClassInformator {

	@Autowired
	private ClassDndService classDndService;
	
	public String getObgectInfo(String classDnd, String archetype) {

		String answer = classDnd + archetype;
		return answer;
	}
}

