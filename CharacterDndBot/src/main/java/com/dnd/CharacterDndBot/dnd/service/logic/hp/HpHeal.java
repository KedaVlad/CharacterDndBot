package com.dnd.CharacterDndBot.dnd.service.logic.hp;

import org.springframework.stereotype.Component;

import com.dnd.CharacterDndBot.dnd.dto.characteristics.Hp;

@Component
public class HpHeal {

	public void heal(Hp hp, int value) {
		hp.setNow(hp.getNow() + value);
		if (hp.getNow() > 0 && hp.isCknoked())
			hp.setCknoked(false);
		if (hp.getNow() > hp.getMax()) {
			hp.setNow(hp.getMax());
		}
	}
}
