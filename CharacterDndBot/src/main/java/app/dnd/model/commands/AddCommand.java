package app.dnd.model.commands;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import app.dnd.model.ObjectDnd;
import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonTypeName("add_command")
@Data
@EqualsAndHashCode(callSuper=false)
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME, property = "type")
public class AddCommand extends InnerCommand {
	private ObjectDnd[] targets;
	
	public static AddCommand create(ObjectDnd... objects) {
		AddCommand answer = new AddCommand();
		answer.targets = objects;
		return answer;
	}
	
}
