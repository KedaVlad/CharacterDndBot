package app.dnd.model.enums;

import lombok.Getter;

public enum Ammunitions {
	SLING_BULLETS("Sling bullets"), ARROWS("Arrows"), BLOWWGUN_NEEDLES("Blowwgun needles"),
	CROSSBOW_BOLTS("Crossbow bolts");

	Ammunitions(String name) {
		this.name = name;
	}

	@Getter
	private String name;

	public String toString() {
		return name;
	}
}