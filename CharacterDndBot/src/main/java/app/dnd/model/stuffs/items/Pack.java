package app.dnd.model.stuffs.items;

import com.fasterxml.jackson.annotation.JsonTypeName;

import app.dnd.model.enums.Packs;

@JsonTypeName("pack")
public class Pack extends Items {
 

	public Pack() {}

	public Pack(Packs type) {
		this.setName(type.getName());
		this.setDescription(type.getDescription());
	}
}
