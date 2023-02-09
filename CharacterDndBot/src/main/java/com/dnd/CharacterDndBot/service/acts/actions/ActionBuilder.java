package com.dnd.CharacterDndBot.service.acts.actions;

import com.dnd.CharacterDndBot.service.dndTable.dndDto.ObjectDnd;

public class ActionBuilder extends BaseActionBuilder<ActionBuilder> {
	
	private String[][] buttons;
	private ObjectDnd objectDnd;

	ActionBuilder() {}

	public ActionBuilder buttons(String[][] buttons) {
		this.buttons = buttons;
		return this;
	}

	//public ActionBuilder objectDnd(ObjectDnd objectDnd) {
	//	this.objectDnd = objectDnd;
	//	return this;
	//}

	public Action build() {
		Action action = new Action();
		action.setName(name);
		action.setReplyButtons(replyButtons);
		action.setMediator(mediator);
		if (this.cloud) {
			action.setCloud(cloud);
			if (buttons == null) {
				this.buttons = new String[][] { { "ELIMINATION" } };
			} else {
				String[][] newButtons = new String[this.buttons.length + 1][];
				for (int i = 0; i < this.buttons.length; i++) {
					newButtons[i] = this.buttons[i];
				}
				newButtons[this.buttons.length] = new String[] { "ELIMINATION" };
				this.buttons = newButtons;
			}
		}
		action.setButtons(buttons);
		action.setObjectDnd(objectDnd);
		action.setLocation(location);
		return action;
	}

	@Override
	protected ActionBuilder self() {
		return this;
	}
}
