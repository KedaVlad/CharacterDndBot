package app.dnd.model.enums;

public enum SaveRolls {
	SR_STRENGTH("SR Strength", Stats.STRENGTH),
	SR_DEXTERITY("SR Dexterity", Stats.DEXTERITY),
	SR_CONSTITUTION("SR Constitution", Stats.CONSTITUTION),
	SR_INTELLIGENCE("SR Intelligence", Stats.INTELLIGENCE),
	SR_WISDOM("SR Wisdom", Stats.WISDOM), 
	SR_CHARISMA("SR Charisma", Stats.CHARISMA);

	private final String name;
	private final Stats stat;

	SaveRolls(String name, Stats stat) {
		this.name = name;
		this.stat = stat;
	}
	
	public Stats getStat() {
		return stat;
	}

	public String getName() {
		return name;
	}
}
