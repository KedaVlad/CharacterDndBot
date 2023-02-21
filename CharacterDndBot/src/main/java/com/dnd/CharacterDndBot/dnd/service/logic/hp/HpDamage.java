package com.dnd.CharacterDndBot.dnd.service.logic.hp;

import org.springframework.stereotype.Component;

import com.dnd.CharacterDndBot.dnd.dto.characteristics.Hp;

@Component
public class HpDamage {

	public void damaged(Hp hp, int value) {
		if (hp.getTimeHp() > 0) {
			hp.setTimeHp(hp.getTimeHp() - value);
			if (hp.getTimeHp() < 0) {
				hp.setNow(hp.getNow() + hp.getTimeHp());
				hp.setTimeHp(0);
			}
		} else {
			hp.setNow(hp.getNow() - value);
		}
		if (hp.getNow() < 0) hp.setCknoked(true);
		if (hp.getNow() < hp.getMax() * -1) hp.setDead(true);
	}

}
