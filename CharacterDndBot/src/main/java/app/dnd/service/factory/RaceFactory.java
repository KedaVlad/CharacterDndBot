package app.dnd.service.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import app.bot.model.act.Act;
import app.bot.model.act.ReturnAct;
import app.bot.model.act.SingleAct;
import app.bot.model.act.actions.Action;
import app.bot.model.user.User;
import app.dnd.dto.CharacterDnd;
import app.dnd.dto.RaceDnd;
import app.dnd.dto.comands.InerComand;
import app.dnd.dto.wrap.RaceDndWrapp;
import app.dnd.service.Executor;
import app.dnd.service.Location;
import app.dnd.util.ArrayToOneColums;
import app.dnd.util.ArrayToThreeColums;
import app.repository.RaceDndWrappService;
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
		log.info("RaceFactory (executrFor) condition: " + action.condition());
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

@Slf4j
@Component
class RaceStartCreate implements Executor<Action> {

	@Autowired
	private RaceDndWrappService raceDndWrappService;
	@Autowired
	private ArrayToThreeColums arrayToThreeColums;

	@Override
	public Act executeFor(Action action, User user) {
		log.info("RaceStartCreate ");
		return ReturnAct.builder()
				.target(START_B)
				.act(SingleAct.builder()
						.name("CreateRace")
						.text("From what family you are?")
						.action(Action.builder()
								.location(Location.RACE_FACTORY)
								.buttons(arrayToThreeColums.rebuild(raceDndWrappService.findDistinctRaceName().toArray(String[]::new)))
								.build())
						.build())
				.build();
	}
}

@Component
class ChooseSubRace implements Executor<Action> {

	@Autowired
	private RaceDndWrappService raceDndWrappService;
	@Autowired
	private ArrayToOneColums arrayToOneColums;

	@Override
	public Act executeFor(Action action, User user) {

		action.setButtons(arrayToOneColums.rebuild(raceDndWrappService.findDistinctSubRaceByRaceName(action.getAnswers()[0]).toArray(String[]::new)));
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
	private RaceDndWrappService raceDndWrappService;

	@Override
	public Act executeFor(Action action, User user) {

		action.setButtons(new String[][] { { "Yeah, right" } });
		
		String text = raceDndWrappService.findDistinctInformationByRaceNameAndSubRace(action.getAnswers()[0], action.getAnswers()[1]);
		return SingleAct.builder()
				.name("checkCondition")
				.text(text + "\nIf not, select another option above.")
				.action(action).build();
	}
}

@Component
class RaceFinishCreate implements Executor<Action> {

	@Autowired
	private ClassFactory classFactory;
	@Autowired
	private RaceDndWrappService raceDndWrappService;
	@Autowired
	private RaceIntegrator raceIntegrator;

	@Override
	public Act executeFor(Action action, User user) {

		String raceName = action.getAnswers()[0];
		String subRace = action.getAnswers()[1];
		RaceDndWrapp raceWrapp = raceDndWrappService.findByRaceNameAndSubRace(raceName, subRace);
		raceIntegrator.integrate(user.getCharactersPool().getActual(), raceWrapp);
		return classFactory.executeFor(Action.builder().build(), user);
	}
}

@Component
class RaceIntegrator {

	@Autowired
	private ScriptReader scriptReader;

	public void integrate(CharacterDnd character, RaceDndWrapp raceWrapp) {
		RaceDnd race = new RaceDnd();
		race.setRaceName(raceWrapp.getRaceName());
		race.setSpeed(raceWrapp.getSpeed());
		race.setSubRace(raceWrapp.getSubRace());
		character.setRace(race);
		character.getMyMemoirs().add(raceWrapp.getInformation());
		for (InerComand comand : raceWrapp.getSpecials()) {
			scriptReader.execute(character, comand);
		}
	}
}



