package app.dnd.service.ability.logic;
import app.dnd.model.enums.Stats;
import app.dnd.util.math.Dice;
import app.bot.model.user.ActualHero;

public interface StatLogic {

	Dice buildDice(ActualHero hero, Stats stat);
	void setup(ActualHero actualHero, int str, int dex, int con, int intl, int wis, int cha);
	void up(ActualHero actualHero, Stats stat, int value);
	boolean isReadyToGame(ActualHero actualHero);
}

