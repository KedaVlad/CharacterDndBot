package app.player.service.stage.event.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import app.dnd.model.actions.Action;
import app.dnd.model.comands.InerComand;
import app.dnd.model.hero.CharacterDnd;
import app.dnd.model.hero.RaceDnd;
import app.dnd.model.wrap.RaceDndWrapp;
import app.dnd.service.data.RaceDndWrappService;
import app.dnd.util.ArrayToColumns;
import app.player.model.act.Act;
import app.player.model.act.ReturnAct;
import app.player.model.act.SingleAct;
import app.player.model.enums.Location;
import app.player.service.stage.Executor;
import app.player.service.stage.event.factory.comandreader.RaceComandReader;
import app.user.model.User;
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
	public Act executeFor(Action action, User user) {
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
	private ArrayToColumns arrayToColumns;

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
								.buttons(arrayToColumns.rebuild(raceDndWrappService.findDistinctRaceName().toArray(String[]::new),3, String.class))
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
	private ArrayToColumns arrayToColumns;

	@Override
	public Act executeFor(Action action, User user) {

		action.setButtons((String[][]) arrayToColumns.rebuild(raceDndWrappService.findDistinctSubRaceByRaceName(action.getAnswers()[0]).toArray(String[]::new),1, String.class));
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
		raceIntegrator.integrate(user.getActualHero().getCharacter(), raceWrapp);
		return classFactory.executeFor(Action.builder().build(), user);
	}
}

@Component
class RaceIntegrator {

	@Autowired
	private RaceComandReader raceComandReader;

	public void integrate(CharacterDnd character, RaceDndWrapp raceWrapp) {
		RaceDnd race = new RaceDnd();
		race.setRaceName(raceWrapp.getRaceName());
		race.setSpeed(raceWrapp.getSpeed());
		race.setSubRace(raceWrapp.getSubRace());
		character.setRace(race);
		for (InerComand comand : raceWrapp.getSpecials()) {
			raceComandReader.execute(character, comand);
		}
	}
}



