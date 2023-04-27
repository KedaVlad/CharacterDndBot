package app.dnd.model.commands;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import app.dnd.model.ObjectDnd;
import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonTypeName("inner_command")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
	@JsonSubTypes.Type(value = AddCommand.class, name = "add_command"),
	@JsonSubTypes.Type(value = UpCommand.class, name = "up_command"),
	@JsonSubTypes.Type(value = CloudCommand.class, name = "cloud_command")})
@Data
@EqualsAndHashCode(callSuper=false)
public abstract class InnerCommand implements ObjectDnd {
	
}
