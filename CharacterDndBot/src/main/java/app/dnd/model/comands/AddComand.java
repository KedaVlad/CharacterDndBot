package app.dnd.model.comands;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import app.dnd.model.ObjectDnd;
import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonTypeName("add_comand")
@Data
@EqualsAndHashCode(callSuper=false)
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME, property = "type")
public class AddComand extends InerComand {
	private ObjectDnd[] targets;
	
	public static AddComand create(ObjectDnd... objects) {
		AddComand answer = new AddComand();
		answer.targets = objects;
		return answer;
	}
	
}
