package app.dnd.model.actions;

import app.player.model.enums.Location;

public abstract class BaseActionBuilder<T extends BaseActionBuilder<T>> {
	
	protected Location location;
	protected String text;

	protected abstract T self();

	public T location(Location location) {
		this.location = location;
		return self();
	}
	
	public T text(String text) {
		this.text = text;
		return self();
	}

}


