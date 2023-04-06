package app.dnd.model.actions;

import app.dnd.util.math.Dice;
import app.dnd.util.math.Formalizer.Roll;

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
		action.setText(text);
		return action;
	}

	@Override
	protected PreRollBuilder self() {
		return this;
	}
}
