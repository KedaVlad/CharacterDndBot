package app.dnd.service.logic.attack;

import app.dnd.model.stuffs.items.Weapon;

public interface AttackInfo {

	public String preAttack(Weapon weapon);
}

class AttackInformator implements AttackInfo {

	@Override
	public String preAttack(Weapon weapon) {
		return weapon.getDescription();
	}
	
}