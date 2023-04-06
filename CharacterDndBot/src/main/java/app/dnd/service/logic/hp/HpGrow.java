package app.dnd.service.logic.hp;

import org.springframework.stereotype.Component;

import app.dnd.model.characteristics.Hp;

@Component
public class HpGrow {

	void grow(Hp hp, int value) {
		hp.setMax(hp.getMax() + value);
		hp.setNow(hp.getMax());
	}
}
