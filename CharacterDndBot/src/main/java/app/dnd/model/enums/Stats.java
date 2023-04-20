package app.dnd.model.enums;

public enum Stats {
	STRENGTH("Strength"),
	DEXTERITY("Dexterity"), 
	CONSTITUTION("Constitution"), 
	INTELLIGENSE("Intelligense"),
	WISDOM("Wisdom"), 
	CHARISMA("Charisma");

	private String name;

	Stats(String name) {
		this.name = name;
	}

	public String toString() {
		return name;
	}
}
