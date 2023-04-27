package app.dnd.service.talants.logic;

import app.dnd.model.enums.Proficiency;
import app.dnd.model.telents.proficiency.Possession;
import app.dnd.util.math.Dice;
import app.bot.model.user.ActualHero;

public interface ProficienciesLogic {

	void addPossession(ActualHero actualHero, Possession possession);

	Dice buildDice(ActualHero actualHero, Proficiency proficiency);
	
	String profMenu(ActualHero actualHero);

	void upProf(ActualHero actualHero, int lvl);
}

