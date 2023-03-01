package app.dnd.service.logic.characteristic;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.dto.characteristics.Stat.Stats;

@Component
public class StatSetup {
	@Autowired
	private StatUp statUp;
	
	public void setup(Long id, int str, int dex, int con, int intl, int wis, int cha)
	{
		statUp.up(id, Stats.STRENGTH, str);
		statUp.up(id, Stats.DEXTERITY, dex);
		statUp.up(id, Stats.CONSTITUTION, con);
		statUp.up(id, Stats.INTELLIGENSE, intl);
		statUp.up(id, Stats.WISDOM, wis);
		statUp.up(id, Stats.CHARISMA, cha);
	}
}
