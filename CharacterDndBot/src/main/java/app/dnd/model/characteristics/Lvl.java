package app.dnd.model.characteristics;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import app.dnd.model.Informator;
import app.dnd.service.logic.lvl.LvlAddExperience;
import lombok.Data;

@Data
@Document(collection = "lvl")
public class Lvl {
	
	@Id
	private Long id;
	private int lvl;
	private int experience;
	
	@Override
	public String getInformation() {
		String answer;
		if (lvl == 20) {
			answer = "LVL: 20(MAX LVL)";
		} else {
			answer = "LVL: " + lvl + "(" + experience + "|" + LvlAddExperience.expPerLvl[lvl]+ ")";
		}
		return answer + "\n";
	}
	
	public static Lvl create(Long id) {
		Lvl lvl = new Lvl();
		lvl.lvl = 1;
		return lvl;
	}
}
