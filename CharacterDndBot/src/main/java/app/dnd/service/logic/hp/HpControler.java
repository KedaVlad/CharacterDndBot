package app.dnd.service.logic.hp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dnd.model.hero.CharacterDnd;

@Service
public class HpControler {

	@Autowired
	private HpBuilderFactory hpBuilderFactory;
	@Autowired
	private HpDamage hpDamage;
	@Autowired
	private HpGrow hpGrow;
	@Autowired
	private HpHeal hpHeal;
	@Autowired
	private TimeHp timeHp;
	
	public void grow(CharacterDnd character, int value) {
		hpGrow.grow(character.getHp(), value);
	}
	
	public void damage(CharacterDnd character, int value) {
		hpDamage.damaged(character.getHp(), value);
	}
	
	public void heal(CharacterDnd character, int value) {
		hpHeal.heal(character.getHp(), value);
	}
	
	
	public void bonusHp(CharacterDnd character, int value) {
		timeHp.add(character.getHp(), value);
	}
	
	public HpBuilderFactory buildHp() {
		return hpBuilderFactory;
	}
}
