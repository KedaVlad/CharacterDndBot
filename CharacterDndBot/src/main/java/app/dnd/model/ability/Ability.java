package app.dnd.model.ability;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import app.dnd.model.enums.SaveRolls;
import app.dnd.model.enums.Skills;
import app.dnd.model.enums.Stats;
import lombok.Data;

@Data
@Document(collection = "ability")
public class Ability {

	@Id
	private String mongoId;
	private Long userId;
	private String ownerName;
	private Stat initiative;
	private Map<Stats, Stat> stats;
	private Map<SaveRolls, SaveRoll> saveRolls;
	private Map<Skills, Skill> skills;


	public int modification(Stats stat) {
		return (stats.get(stat).getValue() - 10) / 2;
	}

	public static Ability build(Long userId, String ownerName){

		Ability target = new Ability();
		target.userId = userId;
		target.ownerName = ownerName;
		target.initiative = Stat.create(Stats.DEXTERITY);
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
