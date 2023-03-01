package app.dnd.dto.characteristics;

import app.dnd.dto.characteristics.Stat.Stats;

public class SaveRoll extends Skill {
 
	public SaveRoll(String name, Stats depends) {
		super(name, depends);
	}

	public enum SaveRolls {
		SR_STRENGTH("SR Strength"), SR_DEXTERITY("SR Dexterity"), SR_CONSTITUTION("SR Constitution"),
		SR_INTELLIGENSE("SR Intelligense"), SR_WISDOM("SR Wisdom"), SR_CHARISMA("SR Charisma");

		private String name;

		SaveRolls(String name) {
			this.name = name;
		}

		public String toString() {
			return name;
		}
	}
}
