package app.dnd.service.ability.logic;
import app.dnd.model.enums.Stats;
import app.dnd.util.math.Dice;
import app.user.model.ActualHero;

public interface StatLogic {

	public Dice buildDice(ActualHero hero, Stats stat);
	public void setup(ActualHero actualHero, int str, int dex, int con, int intl, int wis, int cha);
	public void up(ActualHero actualHero, Stats stat, int value);
	public boolean isReadyToGame(ActualHero actualHero);
}

