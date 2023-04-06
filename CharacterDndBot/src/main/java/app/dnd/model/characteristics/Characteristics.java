package app.dnd.model.characteristics;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import app.dnd.model.enums.SaveRolls;
import app.dnd.model.enums.Skills;
import app.dnd.model.enums.Stats;
import lombok.Data;

@Data
@Document(collection = "characteristics")
public class Characteristics {
	
	@Id
	private Long id;
	private Stat initiative;
	private Map<Stats, Stat> stats;
	private Map<SaveRolls, SaveRoll> saveRolls;
	private Map<Skills, Skill> skills;

	
	public int modificator(Stats stat) {
		return (stats.get(stat).getValue() - 10) / 2;
	}

}
