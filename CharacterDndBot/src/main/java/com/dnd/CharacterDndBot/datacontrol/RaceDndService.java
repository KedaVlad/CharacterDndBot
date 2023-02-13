package com.dnd.CharacterDndBot.datacontrol;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.RaceDnd;

@Service
public class RaceDndService {

	@Autowired
    private RaceDndRepository raseDndRepository;

	public List<String> findDistinctRaceName() {
		return raseDndRepository.findDistinctRaceName();
	}
  
	public List<String> findDistinctSubRaceByRaceName(String className) {
		return raseDndRepository.findDistinctSubRaceByRaceName(className);
	}
    
	public List<RaceDnd>  findByRaceNameAndSubRace(String className, String archetype) {
		return raseDndRepository.findByRaceNameAndSubRace(className, archetype);
	}
    
}