package app.dnd.dto.comands;

import com.fasterxml.jackson.annotation.JsonTypeName;

import app.dnd.dto.ObjectDnd;
import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonTypeName("add_comand")
@Data
@EqualsAndHashCode(callSuper=false)
public class AddComand extends InerComand {
	private ObjectDnd[] targets;
	
	public static AddComand create(ObjectDnd... objects) {
		AddComand answer = new AddComand();
		answer.targets = objects;
		return answer;
	}
	
}
