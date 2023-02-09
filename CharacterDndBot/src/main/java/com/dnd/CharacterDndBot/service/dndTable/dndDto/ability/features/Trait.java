package com.dnd.CharacterDndBot.service.dndTable.dndDto.ability.features;

  

public class Trait extends Feature {
 
	private int id;
	private String name;

	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null || obj.getClass() != this.getClass())
			return false;
		Trait characterDnd = (Trait) obj;
		return id == characterDnd.id && (name == characterDnd.name || (name != null && name.equals(characterDnd.name)));
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + id;
		return result;

	}
}
