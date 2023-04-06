package app.dnd.model.actions;

import app.dnd.util.ButtonName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PreRoll extends BaseAction { 

	String[][] nextStep;
	RollAction roll;
	private String status;
	private boolean criticalMiss;
	private boolean criticalHit;

	PreRoll() {
		nextStep = new String[][] { {ButtonName.ADVANTAGE, ButtonName.BASIC, ButtonName.DISADVANTAGE } };
	}

	public static PreRollBuilder builder() {
		return new PreRollBuilder();
	}

	@Override
	public PreRoll continueAction(String answer) {
		this.status = answer;
		return this;
	}

	@Override
	public String[][] buildButtons() {
		return nextStep;
	}

	@Override
	public boolean hasButtons() {
		return nextStep != null && nextStep.length != 0;
	}

	@Override
	public boolean replyContain(String string) {
		return string.equals(ButtonName.ADVANTAGE) || string.equals(ButtonName.BASIC) || string.equals(ButtonName.DISADVANTAGE);
	}

}
