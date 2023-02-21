package app.dnd.service.logic.hp;

import org.springframework.stereotype.Component;

import app.dnd.dto.characteristics.Hp;

@Component
public class HpInformator {

	public String info(Hp hp) {
		String answer = "HP: " + hp.getNow();
		if (hp.getTimeHp() > 0) {
			answer += "(+" + hp.getTimeHp() + ")";
		}
		if (hp.isDead()) {
			answer += "(DEAD)";
		} else if (hp.isCknoked()) {
			answer += "(cknoked)";
		}
		return answer + "\n";
	}
}
