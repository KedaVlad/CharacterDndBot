package app.dnd.model.comands;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import app.dnd.model.ObjectDnd;
import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonTypeName("iner_comand")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
	@JsonSubTypes.Type(value = AddComand.class, name = "add_comand"),
	@JsonSubTypes.Type(value = UpComand.class, name = "up_comand"),
	@JsonSubTypes.Type(value = CloudComand.class, name = "cloud_comand")})
@Data
@EqualsAndHashCode(callSuper=false)
public abstract class InerComand implements ObjectDnd {
	
}
