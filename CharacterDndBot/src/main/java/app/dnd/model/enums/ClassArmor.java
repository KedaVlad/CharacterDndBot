package app.dnd.model.enums;

public enum ClassArmor {
	LIGHT("Light Armor"), 
	HEAVY("Heavy Armor"), 
	MEDIUM("Medium Armor"), 
	SHIELD("Shield");
	
	ClassArmor(String name) {
		this.name = name;
	}
	
	private String name;
	public String toString() {
		return name;
	}
}