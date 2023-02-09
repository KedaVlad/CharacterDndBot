package com.dnd.CharacterDndBot.service.dndTable.dndService.factoryService;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dnd.CharacterDndBot.service.User;
import com.dnd.CharacterDndBot.service.acts.Act;
import com.dnd.CharacterDndBot.service.acts.ReturnAct;
import com.dnd.CharacterDndBot.service.acts.SingleAct;
import com.dnd.CharacterDndBot.service.acts.actions.Action;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.CharacterDnd;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.ClassDnd;
import com.dnd.CharacterDndBot.service.dndTable.dndService.Executor;
import com.dnd.CharacterDndBot.service.dndTable.dndService.Location;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClassFactory extends Executor<Action> {
	private final static File CLASS_DIRECTORY = new File(
			"C:\\Users\\ALTRON\\eclipse-workspace\\CharacterDndBot\\LocalData\\classes");

	public ClassFactory(Action action) {
		super(action);
	}

	@Override
	public Act executeFor(User user) {
		int condition = 0;
		if (action.getAnswers() != null) condition = action.getAnswers().length;
		switch (condition) {
		case 0:
			return startCreate();
		case 1:
			return chooseArchetype();
		case 2:
			return chooseLvl();
		case 3:
			return checkCondition();
		case 4:
			return finish(user);
		}
		log.error("ClassFactory: out of bounds condition");
		return null;
	}

	private Act startCreate() {
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

	private Act chooseArchetype() {
		action.setButtons(getArchetypeArray(action.getAnswers()[0]));
		return SingleAct.builder()
				.name("ChooseClassArchtype")
				.text(action.getAnswers()[0] + ", realy? Which one?")
				.action(action)
				.build();
	}

	private SingleAct chooseLvl() {
		action.setMediator(true);
		return SingleAct.builder()
				.name("ChooseClassLvl")
				.text("What is your lvl?")
				.action(action)
				.build();
	}

	private SingleAct checkCondition() {
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
					.text(getObgectInfo(action.getAnswers()[0], action.getAnswers()[1])
							+ "\nIf not, select another option above.")
					.action(action)
					.build();
		} else {
			action.getAnswers()[2] = 1 + "";
			action.setButtons(new String[][] { { "Okey" } });
			return SingleAct.builder()
					.name("checkClassCondition")
					.text(lvl + "??? I see you're new here. Let's start with lvl 1.\nAre you satisfied with this option?/n"
							+ getObgectInfo(action.getAnswers()[0], action.getAnswers()[1])
							+ "\nIf not, select another option above.")
					.action(action)
					.build();
		}
	}

	private Act finish(User user) {
		String className = action.getAnswers()[0];
		String archetype = action.getAnswers()[1];
		int lvl = ((Integer) Integer.parseInt(action.getAnswers()[2]));
		integrator(user.getCharactersPool().getActual(), desirializer(className, archetype), lvl);
		return new StatFactory(Action.builder().build()).executeFor(user);
	}

	private ClassDnd desirializer(String className, String archetype) {
		return new ClassDnd();
	}

	private void integrator(CharacterDnd character, ClassDnd clazz, int lvl) {
		character.setDndClass(new ClassDnd[] { clazz });
		// character.getMyMemoirs().add(getObgectInfo(raceName, subRace));
	}

	private static String[][] getClassArray() {
		String[] all = CLASS_DIRECTORY.list();
		String[][] allClasses = new String[all.length][1];
		for (int i = 0; i < all.length; i++) {
			allClasses[i][0] = all[i];
		}
		return allClasses;
	}

	private static String[][] getArchetypeArray(String className) {
		File dirArchetype = new File(CLASS_DIRECTORY + "\\" + className);
		String[] all = dirArchetype.list();
		String[][] allArchetypes = new String[all.length][1];
		for (int i = 0; i < all.length; i++) {
			allArchetypes[i][0] = all[i].replaceAll("([a-zA-Z]*)(.json)", "$1");
		}
		return allArchetypes;
	}

	private static String getObgectInfo(String classDnd, String archetype) {

		String answer = classDnd + archetype;
		return answer;
	}

}
