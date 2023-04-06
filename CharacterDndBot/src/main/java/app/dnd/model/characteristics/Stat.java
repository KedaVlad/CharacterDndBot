package app.dnd.model.characteristics;

import com.fasterxml.jackson.annotation.JsonTypeName;

import app.dnd.model.ObjectDnd;
import app.dnd.model.enums.Stats;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonTypeName("stat")
public class Stat implements ObjectDnd {

	private Stats name;
	private int value = 0;
	private int minValue = 3;
	private int maxValue = 20;
	private int[] elseSourceValue = { 0, 0 };

	public static Stat create(Stats stat) {
		Stat target = new Stat();
		target.name = stat;
		return target;
	}

	
}
