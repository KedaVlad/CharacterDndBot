package app.dnd.model.characteristics;

import com.fasterxml.jackson.annotation.JsonTypeName;

import app.dnd.model.ObjectDnd;
import app.dnd.model.enums.Proficiency;
import app.dnd.model.enums.SaveRolls;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonTypeName("save_roll")
public class SaveRoll implements ObjectDnd {
 
	private SaveRolls core;
	private Proficiency proficiency;

	public static SaveRoll create(SaveRolls saveRoll) {
		SaveRoll target = new SaveRoll();
		target.core = saveRoll;
		return target;
	}
}
