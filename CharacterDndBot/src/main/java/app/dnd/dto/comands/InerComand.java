package app.dnd.dto.comands;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import app.dnd.dto.ObjectDnd;
import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonTypeInfo(include = JsonTypeInfo.As.EXTERNAL_PROPERTY, use = JsonTypeInfo.Id.NAME,  property = "iner_comand")
@JsonSubTypes({
	@JsonSubTypes.Type(value = AddComand.class, name = "add_comand"),
	@JsonSubTypes.Type(value = UpComand.class, name = "up_comand"),
	@JsonSubTypes.Type(value = CloudComand.class, name = "cloud_comand")})
@Data
@EqualsAndHashCode(callSuper=false)
public abstract class InerComand extends ObjectDnd {
	
}
