package app.dnd.model.actions;

import app.dnd.model.ObjectDnd;


public class ActionBuilder extends SingleActionBuilder<ActionBuilder> {
	
	private String[][] buttons;
	private ObjectDnd objectDnd;

	ActionBuilder() {}

	public ActionBuilder objectDnd(ObjectDnd objectDnd) {
		this.objectDnd = objectDnd;
		return this;
	}
	
	public ActionBuilder buttons(String[][] buttons) {
		this.buttons = buttons;
		return this;
	}

	public Action build() {
		Action action = new Action();
		action.setName(name);
		action.setText(text);
		action.setObjectDnd(objectDnd);
		action.setButtons(buttons);
		action.setLocation(location);
		return action;
	}

	@Override
	protected ActionBuilder self() {
		return this;
	}
}
