package app.dnd.model.telents.proficiency;
 
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
	private String mongoId;
	private Long userId;
	private String ownerName;
	private int proficiency;
	private List<Possession> possessions;

	public static Proficiencies build(Long id, String ownerName) {
		Proficiencies proficiencies = new Proficiencies();
		proficiencies.ownerName = ownerName;
		proficiencies.userId = id;
		proficiencies.possessions = new ArrayList<>();
		proficiencies.proficiency = 2;
		return proficiencies;
	}
	
	public int getProfBonus(Proficiency prof) {
		return switch (prof) {
			case BASE -> proficiency;
			case COMPETENCE -> proficiency * 2;
			case HALF -> proficiency / 2;
			default -> 0;
		};
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