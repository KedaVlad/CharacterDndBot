package app.dnd.service.logic.hp;

import org.springframework.stereotype.Component;

import app.dnd.model.characteristics.Hp;

@Component
public class HpHeal {

	void heal(Hp hp, int value) {
		hp.setNow(hp.getNow() + value);
		if (hp.getNow() > 0 && hp.isCknoked())
			hp.setCknoked(false);
		if (hp.getNow() > hp.getMax()) {
			hp.setNow(hp.getMax());
		}
	}
}
