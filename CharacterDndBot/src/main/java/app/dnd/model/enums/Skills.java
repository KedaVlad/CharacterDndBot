package app.dnd.model.enums;

public enum Skills {
	
	ACROBATICS("Acrobatics", Stats.DEXTERITY),
	ANIMAL_HANDING("Animal Handing", Stats.WISDOM), 
	ARCANA("Arcana", Stats.INTELLIGENCE), 
	ATHLETIC("Athletic", Stats.STRENGTH),
	DECEPTION("Deception", Stats.CHARISMA),
	HISTORY("History", Stats.INTELLIGENCE),
	INSIGHT("Insight", Stats.WISDOM),
	INTIMIDATION("Intimidation", Stats.INTELLIGENCE),
	INVESTIGATION("Investigation", Stats.WISDOM), 
	MEDICINE("Medicine", Stats.CHARISMA),
	NATURE("Nature", Stats.INTELLIGENCE), 
	PERCEPTION("Perception", Stats.WISDOM),
	PERFORMANCE("Performance", Stats.CHARISMA), 
	PERSUASION("Persuasion", Stats.CHARISMA),
	RELIGION("Religion", Stats.INTELLIGENCE),
	SLEIGHT_OF_HAND("Sleight of Hand", Stats.DEXTERITY),
	STEALTH("Stealth", Stats.DEXTERITY),
	SURVIVAL("Survival", Stats.WISDOM);

	private final String name;
	private final Stats stat;
	

	Skills(String name, Stats stat) {
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