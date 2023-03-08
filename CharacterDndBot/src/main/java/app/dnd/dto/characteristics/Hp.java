package app.dnd.dto.characteristics;

import com.fasterxml.jackson.annotation.JsonTypeName;

import app.dnd.dto.Informator;
import app.dnd.dto.ObjectDnd;
import app.dnd.dto.Refreshable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonTypeName("hp")
public class Hp extends ObjectDnd implements Refreshable, Informator {

	private int max = 0;
	private int now = 0;
	private int timeHp = 0;
	private boolean cknoked = false;
	private boolean dead = false;
	private int hpBonus  = 0;

	@Override
	public void refresh(Time time) {
		if (time == Time.LONG) {
			now = max;
		}
	}
}
