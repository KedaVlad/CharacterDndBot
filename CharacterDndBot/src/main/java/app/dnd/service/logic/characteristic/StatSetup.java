package app.dnd.service.logic.characteristic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.dto.characteristics.Characteristics;
import app.dnd.dto.characteristics.Stat;

@Component
public class StatSetup {
	@Autowired
	private StatUp statUp;
	
	public void setup(Characteristics characteristics, int str, int dex, int con, int intl, int wis, int cha)
	{
		Stat[] stats = characteristics.getStats();
		statUp.up(stats[0], str);
		statUp.up(stats[1], dex);
		statUp.up(stats[2], con);
		statUp.up(stats[3], intl);
		statUp.up(stats[4], wis);
		statUp.up(stats[5], cha);
	}
}
