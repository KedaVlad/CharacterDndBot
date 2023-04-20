package app.dnd.model.telents.features;

import com.fasterxml.jackson.annotation.JsonTypeName;

import app.dnd.model.Refreshable;
import app.dnd.model.telents.casts.Cast;
import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonTypeName("active_feature")
@Data
@EqualsAndHashCode(callSuper = false)
public class ActiveFeature extends Feature implements Refreshable {

	private int charges;
	private int targetCells;
	private Time time;
	private Cast cast;

	@Override
	public void refresh(Time time) {
		if (this.time.equals(Time.SHORT) || time.equals(Time.LONG)) {
			targetCells = charges;
		}
	}
}
