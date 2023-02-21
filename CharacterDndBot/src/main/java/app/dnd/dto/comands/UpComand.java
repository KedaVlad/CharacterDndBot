package app.dnd.dto.comands;

import com.fasterxml.jackson.annotation.JsonTypeName;

import app.dnd.dto.ObjectDnd;
import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonTypeName("up_comand")
@Data
@EqualsAndHashCode(callSuper=false)
public class UpComand extends InerComand {
	private static final long serialVersionUID = 1L;
	private ObjectDnd object;
	private int value;

}
