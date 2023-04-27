package app.dnd.model.commands;

import java.util.ArrayList;
import java.util.List;

import app.dnd.model.ObjectDnd;
import lombok.Data;

@Data
public class CommandContainer {

	List<ObjectDnd> up = new ArrayList<>();
	List<ObjectDnd> add = new ArrayList<>();
	List<CloudCommand> cloud = new ArrayList<>();
	
}
