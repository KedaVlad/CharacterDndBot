package app.dnd.model.actions;

import app.dnd.model.enums.Roll;
import app.dnd.util.math.Dice;
import app.player.model.enums.Button;

public class PreRollBuilder extends BaseActionBuilder<PreRollBuilder> {

	private RollAction roll;

	PreRollBuilder() {}

	public PreRollBuilder roll(RollAction roll) {
		this.roll = roll;
		this.roll.addDices(RollAction.DicePosition.START, new Dice("D20", 0, Roll.D20));
		return this;
	}

	public PreRoll build() {
		PreRoll action = new PreRoll();
		action.setRoll(roll);
		action.setLocation(location);
		action.setText(text);
		action.setButtons(new String[][] { {Button.ADVANTAGE.NAME, Button.BASIC.NAME, Button.DISADVANTAGE.NAME } });
		return action;
	}

	@Override
	protected PreRollBuilder self() {
		return this;
	}
}
