package app.dnd.model.wrapp;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import app.dnd.model.commands.InnerCommand;
import lombok.Data;

@Data
@Document(collection = "races_test")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME, property = "type")
public class RaceDndWrapp {
	
	@Id
	private String id;
	private String information;
	private String raceName;
	private String subRace;
	private int speed;
	private InnerCommand[] specials;
}
