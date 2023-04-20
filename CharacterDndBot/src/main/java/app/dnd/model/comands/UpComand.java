package app.dnd.model.comands;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import app.dnd.model.ObjectDnd;
import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonTypeName("up_comand")
@Data
@EqualsAndHashCode(callSuper=false)
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME, property = "type")
public class UpComand extends InerComand {
	private ObjectDnd objectDnd;
	public static UpComand create(ObjectDnd objectDnd) {
		UpComand answer = new UpComand();
		answer.objectDnd = objectDnd;
		return answer;
	}
}
