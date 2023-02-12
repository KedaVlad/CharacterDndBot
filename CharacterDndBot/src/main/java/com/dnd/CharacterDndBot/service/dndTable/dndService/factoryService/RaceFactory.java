package com.dnd.CharacterDndBot.service.dndTable.dndService.factoryService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.dnd.CharacterDndBot.service.acts.Act;
import com.dnd.CharacterDndBot.service.acts.ReturnAct;
import com.dnd.CharacterDndBot.service.acts.SingleAct;
import com.dnd.CharacterDndBot.service.acts.actions.Action;
import com.dnd.CharacterDndBot.service.bot.user.User;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.CharacterDnd;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.RaceDnd;
import com.dnd.CharacterDndBot.service.dndTable.dndService.Executor;
import com.dnd.CharacterDndBot.service.dndTable.dndService.Location;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RaceFactory extends Executor<Action> {
	
	private final static File dirRace = new File(
			"C:\\Users\\ALTRON\\eclipse-workspace\\CharacterDndBot\\LocalData\\race");

	public RaceFactory(Action action) {
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
			return chooseSubRace();
		case 2:
			return checkCondition();
		case 3:
			return finish(user);
		}
		log.error("RaceFactory: out of bounds condition");
		return null;
	}

	private Act startCreate() {
		return ReturnAct.builder()
				.target(START_B)
				.act(SingleAct.builder()
						.name("CreateRace")
						.text("From what family you are?")
						.action(Action.builder()
								.location(Location.RACE_FACTORY)
								.buttons(getRaceArray())
								.build())
						.build())
				.build();
	}

	private Act chooseSubRace() {
		action.setButtons(getSubRaceArray(action.getAnswers()[0]));
		return SingleAct.builder()
				.name("ChooseSubRace")
				.text(action.getAnswers()[0] + "? More specifically?")
				.action(action)
				.build();
	}

	private Act checkCondition() {
		action.setButtons(new String[][] { { "Yeah, right" } });
		return SingleAct.builder()
				.name("checkCondition")
				.text(getObgectInfo(action.getAnswers()[0], action.getAnswers()[1])
						+ "\nIf not, select another option above.")
				.action(action).build();
	}

	private Act finish(User user) {
		String raceName = action.getAnswers()[0];
		String subRace = action.getAnswers()[1];
		integrator(user.getCharactersPool().getActual(), desirializer(raceName, subRace));
		return new ClassFactory(Action.builder().build()).executeFor(user);
	}

	private RaceDnd desirializer(String race, String subRace) {
		return new RaceDnd();
	}

	private void integrator(CharacterDnd character, RaceDnd race) {
		character.setRace(race);
		// character.getMyMemoirs().add(getObgectInfo(raceName, subRace));
	}

	private String[][] getRaceArray() {
		String[] all = dirRace.list();
		List<String[]> buttons = new ArrayList<>();

		for (int i = 1; i <= all.length; i += 3) {
			if (((i + 1) > all.length) && ((i + 2) > all.length)) {
				buttons.add(new String[] { all[i - 1] });
			} else if ((i + 2) > all.length) {
				buttons.add(new String[] { all[i - 1], all[i] });
			} else {
				buttons.add(new String[] { all[i - 1], all[i], all[i + 1] });
			}
		}

		String[][] allRaces = buttons.toArray(new String[buttons.size()][]);

		return allRaces;
	}

	private String[][] getSubRaceArray(String race) {
		File dirSubRace = new File(dirRace + "\\" + race);

		String[] all = dirSubRace.list();

		String[][] allRaces = new String[all.length][1];
		for (int i = 0; i < all.length; i++) {
			allRaces[i][0] = all[i].replaceAll("([a-zA-Z]*)(.json)", "$1");
		}

		return allRaces;
	}

	private String getObgectInfo(String race, String subRace) {

		return race + subRace;
	}

}
