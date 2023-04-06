package app.dnd.model.ability.proficiency;
 
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import app.dnd.model.enums.Proficiency;
import lombok.Data;

@Data
@Document(collection = "proficiencies")
public class Proficiencies { 
	
	@Id
	private Long id;
	private int proficiency;
	private List<Possession> possessions;

	public static Proficiencies build(Long id) {
		Proficiencies proficiencies = new Proficiencies();
		proficiencies.id = id;
		proficiencies.possessions = new ArrayList<>();
		proficiencies.proficiency = 2;
		return proficiencies;
	}
	
	public int getProfBonus(Proficiency prof) {
		switch (prof) {
		case BASE:
			return proficiency;
		case COMPETENSE:
			return proficiency * 2;
		case HALF:
			return proficiency / 2;
		default:
			return 0;
		}
	}
	
	public Proficiency findProficiency(String name) {
		for(Possession target: possessions) {
			if(target.getName().contains(name)) {
				return target.getProf();
			}
		}
		return null;
	}

	public boolean checkProficiency(String name) {
		for(Possession target: possessions) {
			if(target.getName().contains(name)) {
				return true;
			}
		}
		return false;
	}
	
	public void upProf(int lvl) {
		if(lvl > 16) {
			proficiency = 6;
		} else if (lvl > 12) {
			proficiency = 5;
		} else if (lvl > 8) {
			proficiency = 4;
		} else if (lvl > 4) {
			proficiency = 3;
		} else {
			proficiency = 2;
		}
	}
}