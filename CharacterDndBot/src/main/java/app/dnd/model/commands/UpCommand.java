package app.dnd.model.commands;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import app.dnd.model.ObjectDnd;
import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonTypeName("up_command")
@Data
@EqualsAndHashCode(callSuper=false)
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME, property = "type")
public class UpCommand extends InnerCommand {
	private ObjectDnd objectDnd;
	public static UpCommand create(ObjectDnd objectDnd) {
		UpCommand answer = new UpCommand();
		answer.objectDnd = objectDnd;
		return answer;
	}
}
