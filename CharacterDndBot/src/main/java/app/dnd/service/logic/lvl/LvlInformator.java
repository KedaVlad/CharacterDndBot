package app.dnd.service.logic.lvl;

import org.springframework.stereotype.Component;

import app.dnd.dto.characteristics.Lvl;

@Component
public class LvlInformator {

	public String info(Lvl lvl) {
		String answer = "LVL: " + lvl.getLvl() + "(" + lvl.getExperience() + "|" + lvl.getExpPerLvl()[lvl.getLvl()]
				+ ")";
		if (lvl.getLvl() == 20) {
			answer = "LVL: 20(MAX LVL)";
		}
		return answer + "\n";
	}
}
