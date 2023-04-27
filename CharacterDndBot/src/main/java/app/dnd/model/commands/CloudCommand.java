package app.dnd.model.commands;

import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonTypeName("cloud_command")
@Data
@EqualsAndHashCode(callSuper=false)
public class CloudCommand extends InnerCommand {
	protected String name;
	protected String text;
	
	public static CloudCommand create(String name, String text) {
		CloudCommand answer = new CloudCommand();
		answer.name = name;
		answer.text = text;
		return answer;
	}
}
