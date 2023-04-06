package app.dnd.service.logic.characteristic;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.dnd.model.characteristics.Characteristics;
import app.dnd.model.characteristics.SaveRoll;
import app.dnd.model.characteristics.Skill;
import app.dnd.model.characteristics.Stat;
import app.dnd.model.enums.SaveRolls;
import app.dnd.model.enums.Skills;
import app.dnd.model.enums.Stats;
import app.dnd.repository.CharacteristicsRepository;

@Transactional
@Service
public class CharacteristicsService {

	@Autowired
	private CharacteristicsRepository characteristicsRepository;

	public Characteristics getById(Long id) {
		Optional<Characteristics> userOptional = characteristicsRepository.findById(id);
		if (userOptional.isPresent()) {
			return userOptional.get();
		} else {
			return CharacteristicFactory.build(id);
		}
	}

	public void save(Characteristics characteristics) {
		characteristicsRepository.save(characteristics);
	}

}

class CharacteristicFactory { 
	
	public static Characteristics build(Long id){
		
		Characteristics target = new Characteristics();
		target.setId(id);
		target.setInitiative(Stat.create(Stats.DEXTERITY));
		Map<Stats, Stat> stats = new LinkedHashMap<>();
		Map<Skills, Skill> skills = new LinkedHashMap<>();
		Map<SaveRolls, SaveRoll> saveRolls = new LinkedHashMap<>();
		for(Stats stat: Stats.values()) {
			stats.put(stat, Stat.create(stat));
		}
		for(SaveRolls saveRoll: SaveRolls.values()) {
			saveRolls.put(saveRoll, SaveRoll.create(saveRoll));
		}
		for(Skills skill: Skills.values()) {
			skills.put(skill, Skill.create(skill));
		}
		target.setStats(stats);
		target.setSaveRolls(saveRolls);
		target.setSkills(skills);
		
		return target;
		
	}
}