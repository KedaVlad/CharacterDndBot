package com.dnd.CharacterDndBot.dnd.service.logic.hp;

import org.springframework.stereotype.Component;

import com.dnd.CharacterDndBot.dnd.dto.characteristics.Hp;

@Component
public class HpGrow {

	public void grow(Hp hp, int value) {
		hp.setMax(hp.getMax() + value);
		hp.setNow(hp.getMax());
	}
}
