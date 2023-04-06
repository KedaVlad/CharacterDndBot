package app.dnd.service.logic;

import org.springframework.beans.factory.annotation.Autowired;

import app.dnd.service.characterpool.CharacterPoolLogic;
import app.dnd.service.logic.attack.AttackLogic;
import app.dnd.service.logic.characteristic.CharacteristicLogic;
import app.dnd.service.logic.talants.TalantsLogic;

public interface HeroLogic {

	public CharacteristicLogic characteristic();
	public TalantsLogic talants();
	public AttackLogic attack();
	public CharacterPoolLogic characterPool();
	
}

class HeroFacade implements HeroLogic {
	
	@Autowired
	private CharacteristicLogic characteristicLogic;
	@Autowired
	private TalantsLogic talantsLogic;
	@Autowired
	private AttackLogic attackLogic;
	@Autowired
	private CharacterPoolLogic characterPoolLogic;
	
	@Override
	public CharacteristicLogic characteristic() {
		return characteristicLogic;
	}
	
	@Override
	public TalantsLogic talants() {
		return talantsLogic;
	}

	@Override
	public AttackLogic attack() {
		return attackLogic;
	}

	@Override
	public CharacterPoolLogic characterPool() {
		return characterPoolLogic;
	}
}

