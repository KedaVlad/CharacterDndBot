package com.dnd.CharacterDndBot.service.dndTable.dndService.factoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.dnd.CharacterDndBot.datacontrol.ClassDndService;
import com.dnd.CharacterDndBot.datacontrol.RaceDndService;
import com.dnd.CharacterDndBot.service.acts.Act;
import com.dnd.CharacterDndBot.service.acts.ReturnAct;
import com.dnd.CharacterDndBot.service.acts.SingleAct;
import com.dnd.CharacterDndBot.service.acts.actions.Action;
import com.dnd.CharacterDndBot.service.bot.user.User;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.CharacterDnd;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.RaceDnd;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.comands.InerComand;
import com.dnd.CharacterDndBot.service.dndTable.dndService.Executor;
import com.dnd.CharacterDndBot.service.dndTable.dndService.Location;
import com.dnd.CharacterDndBot.service.dndTable.util.ArrayToOneColums;
import com.dnd.CharacterDndBot.service.dndTable.util.ArrayToThreeColums;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RaceFactory implements Executor<Action> {

	@Autowired
	private RaceStartCreate raceStartCreate;
	@Autowired
	private ChooseSubRace chooseSubRace;
	@Autowired
	private RaceCheckCondition raceCheckCondition;
	@Autowired
	private RaceFinishCreate raceFinishCreate;

	@Override
	public Act executeFor(Action action,User user) {
		switch (action.condition()) {
		case 0:
			return raceStartCreate.executeFor(action, user);
		case 1:
			return chooseSubRace.executeFor(action, user);
		case 2:
			return raceCheckCondition.executeFor(action, user);
		case 3:
			return raceFinishCreate.executeFor(action, user);
		}
		log.error("RaceFactory: out of bounds condition");
		return null;
	}
}

@Component
class RaceStartCreate implements Executor<Action> {

	@Autowired
	private RaceDndService raceDndService;
	@Autowired
	private ArrayToThreeColums arrayToThreeColums;

	@Override
	public Act executeFor(Action action, User user) {

		return ReturnAct.builder()
				.target(START_B)
				.act(SingleAct.builder()
						.name("CreateRace")
						.text("From what family you are?")
						.action(Action.builder()
								.location(Location.RACE_FACTORY)
								.buttons(arrayToThreeColums.rebuild(raceDndService.findDistinctRaceName().toArray(String[]::new)))
								.build())
						.build())
				.build();
	}
}

@Component
class ChooseSubRace implements Executor<Action> {

	@Autowired
	private RaceDndService raceDndService;
	@Autowired
	private ArrayToOneColums arrayToOneColums;

	@Override
	public Act executeFor(Action action, User user) {

		action.setButtons(arrayToOneColums.rebuild(raceDndService.findDistinctSubRaceByRaceName(action.getAnswers()[0]).toArray(String[]::new)));
		return SingleAct.builder()
				.name("ChooseSubRace")
				.text(action.getAnswers()[0] + "? More specifically?")
				.action(action)
				.build();
	}
}

@Component
class RaceCheckCondition implements Executor<Action> {

	@Autowired
	private RaceInformator raceInformator;
	
	@Override
	public Act executeFor(Action action, User user) {

		action.setButtons(new String[][] { { "Yeah, right" } });
		return SingleAct.builder()
				.name("checkCondition")
				.text(raceInformator.getObgectInfo(action.getAnswers()[0], action.getAnswers()[1])
						+ "\nIf not, select another option above.")
				.action(action).build();
	}
}

@Component
class RaceFinishCreate implements Executor<Action> {

	@Autowired
	private ClassFactory classFactory;
	@Autowired
	private RaceDndService raceDndService;
	@Autowired
	private RaceIntegrator raceIntegrator;

	@Override
	public Act executeFor(Action action, User user) {

		String raceName = action.getAnswers()[0];
		String subRace = action.getAnswers()[1];
		RaceDnd race = raceDndService.findByRaceNameAndSubRace(raceName, subRace).get(0);
		raceIntegrator.integrate(user.getCharactersPool().getActual(), race);
		return classFactory.executeFor(Action.builder().build(), user);
	}
}

@Component
class RaceIntegrator {

	@Autowired
	private ScriptReader scriptReader;
	@Autowired
	private ClassInformator classInformator;

	public void integrate(CharacterDnd character, RaceDnd race) {
		character.setRace(race);
		for (InerComand comand : race.getSpecials()) {
			scriptReader.execute(character, comand);
		}
	}
}

@Component
class RaceInformator {

	@Autowired
	private ClassDndService classDndService;

	public String getObgectInfo(String classDnd, String archetype) {

		String answer = classDnd + archetype;
		return answer;
	}
}


