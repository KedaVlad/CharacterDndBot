package app.dnd.dto.characteristics;


import com.fasterxml.jackson.annotation.JsonTypeName;

import app.dnd.dto.ObjectDnd;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@JsonTypeName("stat")
@EqualsAndHashCode(callSuper=false)
public class Stat extends ObjectDnd {

	private static final long serialVersionUID = 1L;
	private Stats name;
	private int value = 0;
	private int minValue = 3;
	private int maxValue = 20;
	private int[] elseSourceValue = { 0, 0 };

	public Stat(Stats name) {
		this.name = name;
	}


    @JsonTypeName("stats")
	public enum Stats {
		STRENGTH("Strength"), DEXTERITY("Dexterity"), CONSTITUTION("Constitution"), INTELLIGENSE("Intelligense"),
		WISDOM("Wisdom"), CHARISMA("Charisma");

		private String name;

		Stats(String name) {
			this.name = name;
		}

		public String toString() {
			return name;
		}
	}
}
