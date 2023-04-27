package app.dnd.model.telents.features;

import com.fasterxml.jackson.annotation.JsonTypeName;

import app.dnd.model.commands.InnerCommand;
import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonTypeName("inner_feature")
@Data
@EqualsAndHashCode(callSuper = false)
public class InnerFeature extends Feature {
	private InnerCommand[] command;
	
	public static InnerFeature create(String string, String string2, InnerCommand... commands) {

		InnerFeature answer = new InnerFeature();
		answer.setName(string);
		answer.setDescription(string2);
		answer.command = commands;
		
		return answer;
	}
}
