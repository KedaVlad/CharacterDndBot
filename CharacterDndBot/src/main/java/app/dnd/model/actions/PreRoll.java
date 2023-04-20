package app.dnd.model.actions;

import com.fasterxml.jackson.annotation.JsonTypeName;

import app.player.model.enums.Button;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonTypeName("pre_roll")
public class PreRoll extends SingleAction { 

	RollAction roll;
	private String status;
	private boolean criticalMiss;
	private boolean criticalHit;

	public static PreRollBuilder builder() {
		return new PreRollBuilder();
	}

	@Override
	public PreRoll continueStage(String answer) {
		this.status = answer;
		return this;
	}

	@Override
	public boolean hasButtons() {
		return true;
	}

	@Override
	public boolean containButton(String string) {
		return string.equals(Button.BASIC.NAME) || string.equals(Button.BASIC.NAME) || string.equals(Button.ADVANTAGE.NAME);
	}

	@Override
	public String[][] buildButton() {
		return getButtons();
	}

}
