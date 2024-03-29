package app.dnd.service.race;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dnd.model.wrapp.RaceDndWrapp;
import app.dnd.repository.RaceDndWrappRepository;

@Service
public class RaceDndWrappService {

	@Autowired
	private RaceDndWrappRepository raceDndWrappRepository;

	public List<String> findDistinctRaceName() {
		return raceDndWrappRepository.findDistinctRaceName();
	}

	public List<String> findDistinctSubRaceByRaceName(String raceName) {
		return raceDndWrappRepository.findDistinctSubRaceByRaceName(raceName);
	}

	public String findDistinctInformationByRaceNameAndSubRace(String raceName, String subRace) {
		return raceDndWrappRepository.findDistinctInformationByRaceNameAndSubRace(raceName, subRace);
	}

	public RaceDndWrapp findByRaceNameAndSubRace(String raceName, String subRace) {
		return raceDndWrappRepository.findByRaceNameAndSubRace(raceName, subRace);
	}
	
}