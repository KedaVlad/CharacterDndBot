package com.dnd.CharacterDndBot.service.acts.actions;

import com.dnd.CharacterDndBot.service.dndTable.dndMath.Dice;
import com.dnd.CharacterDndBot.service.dndTable.dndMath.Formalizer.Roll;

public class PreRollBuilder extends BaseActionBuilder<PreRollBuilder> {

	private RollAction roll;

	PreRollBuilder() {}

	public PreRollBuilder roll(RollAction roll) {
		this.roll = roll;
		this.roll.addDicesToStart(new Dice("D20", 0, Roll.D20));
		return this;
	}

	public PreRoll build() {
		PreRoll action = new PreRoll();
		action.setReplyButtons(replyButtons);
		action.setAction(roll);
		action.setLocation(location);
		action.setName(name);
		return action;
	}

	@Override
	protected PreRollBuilder self() {
		return this;
	}
}
