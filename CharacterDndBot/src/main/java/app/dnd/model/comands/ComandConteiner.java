package app.dnd.model.comands;

import java.util.List;

import app.dnd.model.ObjectDnd;
import lombok.Data;

@Data
public class ComandConteiner {

	List<ObjectDnd> up;
	List<ObjectDnd> add;
	List<CloudComand> cloud;
	
}
