package app.dnd.service.logic.characteristic;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.dto.CharacterDnd;
import app.dnd.dto.characteristics.Stat.Stats;

@Component
public class StatSetup {
	@Autowired
	private StatUp statUp;
	
	public void setup(CharacterDnd character, int str, int dex, int con, int intl, int wis, int cha)
	{
		statUp.up(character, Stats.STRENGTH, str);
		statUp.up(character, Stats.DEXTERITY, dex);
		statUp.up(character, Stats.CONSTITUTION, con);
		statUp.up(character, Stats.INTELLIGENSE, intl);
		statUp.up(character, Stats.WISDOM, wis);
		statUp.up(character, Stats.CHARISMA, cha);
	}
}
