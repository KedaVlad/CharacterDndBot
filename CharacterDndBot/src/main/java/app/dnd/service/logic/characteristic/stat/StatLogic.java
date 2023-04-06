package app.dnd.service.logic.characteristic.stat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.enums.Stats;
import app.dnd.util.math.Dice;

public interface StatLogic {

	public Dice buildDice(Long id, Stats stat);
	public void setup(Long id, int str, int dex, int con, int intl, int wis, int cha);
	public void up(Long id, Stats stat, int value);
}

@Component
class StatFacade implements StatLogic {

	@Autowired
	private StatDiceBuilder statDiceBuilder;
	@Autowired
	private StatSetup statSetup;
	@Autowired
	private StatUp statUp;
	
	@Override
	public Dice buildDice(Long id, Stats stat) {
		return statDiceBuilder.build(id, stat);
	}


	@Override
	public void setup(Long id, int str, int dex, int con, int intl, int wis, int cha) {
		statSetup.setup(id, str, dex, con, intl, wis, cha);
	}

	@Override
	public void up(Long id, Stats stat, int value) {
		statUp.up(id, stat, value);
	}
}