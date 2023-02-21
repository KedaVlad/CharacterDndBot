package com.dnd.CharacterDndBot.dnd.dto.stuffs.items;

import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonTypeName("ammunition")
@Data
@EqualsAndHashCode(callSuper = false)
public class Ammunition extends Items {

	private static final long serialVersionUID = 1L;
	private int value;

	public Ammunition() {}

	public Ammunition(Ammunitions type) {
		this.setName(type.name);
	}

	public String getDescription() {
		return this.toString();
	}

	public enum Ammunitions {
		SLING_BULLETS("Sling bullets"), ARROWS("Arrows"), BLOWWGUN_NEEDLES("Blowwgun needles"),
		CROSSBOW_BOLTS("Crossbow bolts");

		Ammunitions(String name) {
			this.name = name;
		}

		private String name;

		public String toString() {
			return name;
		}
	}

	public String toString() {
		return getName() + "(" + value + ")";
	}

	public void addValue(int value) {
		this.value += value;
	}

}
