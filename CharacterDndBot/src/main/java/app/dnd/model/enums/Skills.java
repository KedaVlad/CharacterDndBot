package app.dnd.model.enums;

public enum Skills {
	
	ACROBATICS("Acrobatics", Stats.DEXTERITY),
	ANIMAL_HANDING("Animal Handing", Stats.WISDOM), 
	ARCANA("Arcana", Stats.INTELLIGENSE), 
	ATHLETIX("Athletix", Stats.STRENGTH),
	DECEPTION("Deception", Stats.CHARISMA),
	HISTORY("History", Stats.INTELLIGENSE),
	INSIGHT("Insight", Stats.WISDOM),
	INTIMIDATION("Intimidation", Stats.INTELLIGENSE),
	INVESTIGATION("Investigation", Stats.WISDOM), 
	MEDICINE("Medicine", Stats.CHARISMA),
	NATURE("Nature", Stats.INTELLIGENSE), 
	PERCEPTION("Perception", Stats.WISDOM),
	PERFORMANCE("Performance", Stats.CHARISMA), 
	PERSUASION("Persuasion", Stats.CHARISMA),
	RELIGION("Religion", Stats.INTELLIGENSE),
	SLEIGHT_OF_HAND("Sleigth of Hand", Stats.DEXTERITY),
	STELTH("Stelth", Stats.DEXTERITY), 
	SURVIVAL("Survival", Stats.WISDOM);

	private String name;
	private Stats stat;
	

	Skills(String name, Stats stat) {
		this.name = name;
	}

	public Stats getStat() {
		return this.stat;
	}
	
	public String toString() {
		return name;
	}

}