package app.dnd.model.stuffs.items;

import com.fasterxml.jackson.annotation.JsonTypeName;

import app.dnd.model.enums.Ammunitions;
import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonTypeName("ammunition")
@Data
@EqualsAndHashCode(callSuper = false)
public class Ammunition extends Items {

	private int value;

	public Ammunition() {}

	public Ammunition(Ammunitions type) {
		this.setName(type.getName());
	}

	public String getDescription() {
		return this.toString();
	}

	public String toString() {
		return getName() + "(" + value + ")";
	}

	public void addValue(int value) {
		this.value += value;
	}

}
