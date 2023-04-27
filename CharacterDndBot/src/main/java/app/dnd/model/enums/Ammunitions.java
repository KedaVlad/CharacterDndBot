package app.dnd.model.enums;

import lombok.Getter;

public enum Ammunitions {
	SLING_BULLETS("Sling bullets"), ARROWS("Arrows"), BLOWGUN_NEEDLES("Blowgun needles"),
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