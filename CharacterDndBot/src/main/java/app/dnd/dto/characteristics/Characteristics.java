package app.dnd.dto.characteristics;

import java.io.Serializable;

import app.dnd.dto.Informator;
import app.dnd.dto.characteristics.SaveRoll.SaveRolls;
import app.dnd.dto.characteristics.Skill.Skills;
import app.dnd.dto.characteristics.Stat.Stats;
import lombok.Data;

@Data
public class Characteristics implements Informator, Serializable {
	
	private static final long serialVersionUID = 1L;
	private Skill initiative;
	private Stat[] stats;
	private Skill[] skills;
	private SaveRoll[] saveRolls;

	{

		stats = new Stat[] { new Stat(Stats.STRENGTH), new Stat(Stats.DEXTERITY), new Stat(Stats.CONSTITUTION),
				new Stat(Stats.INTELLIGENSE), new Stat(Stats.WISDOM), new Stat(Stats.CHARISMA) };
		saveRolls = new SaveRoll[] { new SaveRoll(SaveRolls.SR_STRENGTH.toString(), Stats.STRENGTH),
				new SaveRoll(SaveRolls.SR_DEXTERITY.toString(), Stats.DEXTERITY),
				new SaveRoll(SaveRolls.SR_CONSTITUTION.toString(), Stats.CONSTITUTION),
				new SaveRoll(SaveRolls.SR_INTELLIGENSE.toString(), Stats.INTELLIGENSE),
				new SaveRoll(SaveRolls.SR_WISDOM.toString(), Stats.WISDOM),
				new SaveRoll(SaveRolls.SR_CHARISMA.toString(), Stats.CHARISMA) };
		skills = new Skill[] { new Skill(Skills.ACROBATICS.toString(), Stats.DEXTERITY),
				new Skill(Skills.ANIMAL_HANDING.toString(), Stats.WISDOM),
				new Skill(Skills.ARCANA.toString(), Stats.INTELLIGENSE),
				new Skill(Skills.ATHLETIX.toString(), Stats.STRENGTH),
				new Skill(Skills.DECEPTION.toString(), Stats.CHARISMA),
				new Skill(Skills.HISTORY.toString(), Stats.INTELLIGENSE),
				new Skill(Skills.INSIGHT.toString(), Stats.WISDOM),
				new Skill(Skills.INTIMIDATION.toString(), Stats.CHARISMA),
				new Skill(Skills.INVESTIGATION.toString(), Stats.INTELLIGENSE),
				new Skill(Skills.MEDICINE.toString(), Stats.WISDOM),
				new Skill(Skills.NATURE.toString(), Stats.INTELLIGENSE),
				new Skill(Skills.PERCEPTION.toString(), Stats.WISDOM),
				new Skill(Skills.PERFORMANCE.toString(), Stats.CHARISMA),
				new Skill(Skills.PERSUASION.toString(), Stats.CHARISMA),
				new Skill(Skills.RELIGION.toString(), Stats.INTELLIGENSE),
				new Skill(Skills.SLEIGHT_OF_HAND.toString(), Stats.DEXTERITY),
				new Skill(Skills.STELTH.toString(), Stats.DEXTERITY),
				new Skill(Skills.SURVIVAL.toString(), Stats.WISDOM) };
	}
}
