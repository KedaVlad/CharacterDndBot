package com.dnd.CharacterDndBot.datacontrol;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.dnd.CharacterDndBot.service.dndTable.dndDto.RaceDnd;

@Repository
public interface RaceDndRepository extends MongoRepository<RaceDnd, String> {
    
	List<String> findDistinctRaceName();
	
	List<String> findDistinctSubRaceByRaceName(String raceName);
    
	List<RaceDnd> findByRaceNameAndSubRace(String brand, String archetype);
}
