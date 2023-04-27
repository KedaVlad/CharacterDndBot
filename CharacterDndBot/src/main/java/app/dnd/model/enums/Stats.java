package app.dnd.model.enums;

public enum Stats {
	STRENGTH("Strength"),
	DEXTERITY("Dexterity"), 
	CONSTITUTION("Constitution"), 
	INTELLIGENCE("Intelligence"),
	WISDOM("Wisdom"), 
	CHARISMA("Charisma");

	private final String name;

	Stats(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
