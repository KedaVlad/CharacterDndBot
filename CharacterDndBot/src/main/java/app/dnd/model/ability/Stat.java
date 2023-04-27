package app.dnd.model.ability;

import com.fasterxml.jackson.annotation.JsonTypeName;

import app.dnd.model.ObjectDnd;
import app.dnd.model.enums.Stats;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonTypeName("stat")
public class Stat implements ObjectDnd {

	private Stats core;
	private int value = 0;
	private int minValue = 3;
	private int maxValue = 20;
	private int[] elseSourceValue = { 0, 0 };

	public static Stat create(Stats stat) {
		Stat target = new Stat();
		target.core = stat;
		return target;
	}
	
	public void up(int value) {
		this.value += value;
		if (this.value > maxValue) {
			this.value = maxValue;
		} else if(this.value < minValue) {
			this.value = minValue;
		}
	}

}
