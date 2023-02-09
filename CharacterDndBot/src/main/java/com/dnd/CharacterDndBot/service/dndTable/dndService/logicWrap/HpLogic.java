package com.dnd.CharacterDndBot.service.dndTable.dndService.logicWrap;

import com.dnd.CharacterDndBot.service.dndTable.dndDto.CharacterDnd;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.characteristics.Hp;

public class HpLogic implements CharacterLogic{

	private Hp hp;
	@Override
	public void initialize(CharacterDnd character) {
		this.hp = character.getHp();
	}

	public void grow(int value) {
		hp.setMax(hp.getMax() + value);
		hp.setNow(hp.getMax());
	}

	public void damaged(int value) {
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

	public void heal(int value) {
		hp.setNow(hp.getNow() + value);
		if (hp.getNow() > 0 && hp.isCknoked())
			hp.setCknoked(false);
		if (hp.getNow() > hp.getMax()) {
			hp.setNow(hp.getMax());
		}
	}

	public String info() {
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
