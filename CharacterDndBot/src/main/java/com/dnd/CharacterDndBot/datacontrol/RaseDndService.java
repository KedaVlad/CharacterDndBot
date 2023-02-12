package com.dnd.CharacterDndBot.datacontrol;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.RaceDnd;

@Service
public class RaseDndService {

	@Autowired
    private RaceDndRepository raseDndRepository;

	public List<String> findDistinctClassName() {
		return raseDndRepository.findDistinctRaceName();
	}
  
	public List<String> findDistinctArchetypeByClassName(String className) {
		return raseDndRepository.findDistinctSubRaceByRaceName(className);
	}
    
	public List<RaceDnd>  findByClassNameAndArchetype(String className, String archetype) {
		return raseDndRepository.findByRaceNameAndSubRace(className, archetype);
	}
    
}