package app.dnd.model.stuffs.items;

import com.fasterxml.jackson.annotation.JsonTypeName;

import app.dnd.model.enums.Tools;

@JsonTypeName("tool")
public class Tool extends Items {

	public Tool() {}

	public Tool(Tools type) {
		this.setDescription(type.getText());
		this.setName(type.getName());
	}
}
