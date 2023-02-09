package com.dnd.CharacterDndBot.service.dndTable.dndDto;

public interface Refreshable {
	
	public enum Time implements ObjectDnd {
		LONG("LONG"), SHORT("SHORT");

		String name;

		Time(String name) {
			this.name = name;
		}

		public String toString() {
			return name;
		}

	}

	abstract void refresh(Time time);
}
