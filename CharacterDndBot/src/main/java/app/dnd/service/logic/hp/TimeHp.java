package app.dnd.service.logic.hp;

import org.springframework.stereotype.Component;

import app.dnd.dto.characteristics.Hp;

@Component
public class TimeHp {

	void add(Hp hp, int value) {
		hp.setTimeHp(value);
	}
}
