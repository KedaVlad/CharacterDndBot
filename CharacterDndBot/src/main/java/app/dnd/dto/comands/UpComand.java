package app.dnd.dto.comands;

import com.fasterxml.jackson.annotation.JsonTypeName;

import app.dnd.dto.ObjectDnd;
import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonTypeName("up_comand")
@Data
@EqualsAndHashCode(callSuper=false)
public class UpComand extends InerComand {
	private ObjectDnd object;
	public static UpComand create(ObjectDnd objects) {
		UpComand answer = new UpComand();
		answer.object = objects;
		return answer;
	}
}
