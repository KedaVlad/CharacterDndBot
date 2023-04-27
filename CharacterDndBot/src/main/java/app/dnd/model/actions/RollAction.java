package app.dnd.model.actions;

import com.fasterxml.jackson.annotation.JsonTypeName;

import app.dnd.model.enums.Proficiency;
import app.dnd.model.enums.Stats;
import app.dnd.util.math.Dice;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Arrays;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonTypeName("roll")
public class RollAction extends SingleAction {

	private Stats depends;
	private Proficiency proficiency;
	private List<Dice> base;

	RollAction() {
	}

	public static RollActionBuilder buider() {
		return new RollActionBuilder();
	}

	public void addDices(DicePosition position, Dice... dices) {
		if (dices == null || dices.length == 0) {
			return;
		}

		switch (position) {
			case START:
				base.addAll(0, Arrays.asList(dices));
				break;
			case END:
				base.addAll(Arrays.asList(dices));
				break;
			default:
				throw new IllegalArgumentException("Invalid position: " + position);
		}
	}

	public enum DicePosition {
		START,
		END;
	}

	public Stats getDepends() {
		return depends;
	}

	public boolean isProficiency() {
		return proficiency != null;
	}

	public Proficiency getProficiency() {
		return proficiency;
	}

	public void setDepends(Stats depends) {
		this.depends = depends;
	}

	public void setProficiency(Proficiency proficiency) {
		this.proficiency = proficiency;
	}


	@Override
	public BaseAction continueStage(String key) {
		return this;
	}

	@Override
	public boolean hasButtons() {
		return false;
	}

	@Override
	public boolean containButton(String string) {
		return false;
	}

	@Override
	public String[][] buildButton() {
		return null;
	}
}
