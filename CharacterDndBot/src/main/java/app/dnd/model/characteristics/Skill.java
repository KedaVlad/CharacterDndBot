package app.dnd.model.characteristics;
 
import com.fasterxml.jackson.annotation.JsonTypeName;

import app.dnd.model.ObjectDnd;
import app.dnd.model.enums.Proficiency;
import app.dnd.model.enums.Skills;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonTypeName("skill")
public class Skill implements ObjectDnd {

	private Skills core;
	private Proficiency proficiency;

	public static Skill create(Skills skill) {
		Skill target = new Skill();
		target.core = skill;
		return target;
	}

}
