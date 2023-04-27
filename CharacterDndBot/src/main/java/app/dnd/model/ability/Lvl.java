package app.dnd.model.ability;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import app.dnd.service.lvl.LvlLogic;
import lombok.Data;

@Data
@Document(collection = "lvl")
public class Lvl {
	
	@Id
	private String mongoId;
	private Long userId;
	private String ownerName;
	private int lvl;
	private int experience;
	

	public String getShortInfo() {
		String answer;
		if (lvl == 20) {
			answer = "LVL: 20(MAX LVL)";
		} else {
			answer = "LVL: " + lvl + "(" + experience + "|" + LvlLogic.expPerLvl[lvl]+ ")";
		}
		return answer + "\n";
	}
	
	public static Lvl create(Long id, String ownerName) {
		Lvl lvl = new Lvl();
		lvl.userId = id;
		lvl.ownerName = ownerName;
		lvl.lvl = 1;
		return lvl;
	}
}
