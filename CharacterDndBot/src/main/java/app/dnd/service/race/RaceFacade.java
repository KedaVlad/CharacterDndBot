package app.dnd.service.race;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.hero.RaceDnd;
import app.dnd.model.wrapp.RaceDndWrapp;
import app.bot.model.user.ActualHero;

@Component
public class RaceFacade implements RaceLogic {

	@Autowired
	private RaceDndService raceDndService;
	@Autowired
	private RaceDndWrappService raceDndWrappService;
	@Autowired
	private RaceComandReader raceComandReader;

	@Override
	public boolean isReadyToGame(ActualHero actualHero) {
		return raceDndService.findByUserIdAndOwnerName(actualHero.getId(), actualHero.getName()).getRaceName() != null;
	}

	@Override
	public void setRace(ActualHero actualHero, String raceName, String subRace) {
		RaceDndWrapp raceWrapp = raceDndWrappService.findByRaceNameAndSubRace(raceName, subRace);
		RaceDnd race = new RaceDnd();
		race.setUserId(actualHero.getId());
		race.setOwnerName(actualHero.getName());
		race.setRaceName(raceWrapp.getRaceName());
		race.setSpeed(raceWrapp.getSpeed());
		race.setSubRace(raceWrapp.getSubRace());
		raceDndService.save(race);
		raceComandReader.execute(actualHero, raceWrapp.getSpecials());
	}

}
