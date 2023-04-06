package app.dnd.service.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.service.characterpool.CharactersPoolService;
import app.dnd.service.logic.attack.AttackButton;
import app.dnd.service.logic.characteristic.CharacteristicButton;
import app.dnd.service.logic.talants.TalantsButton;
import app.user.model.CharactersPool;


public interface ButtonLogic {

	public TalantsButton talants();
	public CharacteristicButton characteristic();
	public AttackButton attack();
	public CharacterPoolButton characterPool();
	
}

@Component
class ButtonBuilder implements ButtonLogic {

	@Autowired
	public TalantsButton talantsButtonsBuilder;
	@Autowired
	public CharacteristicButton characteristicButton;
	@Autowired
	public AttackButton attackButton;
	@Autowired
	public CharacterPoolButton characterPoolButton;

	@Override
	public TalantsButton talants() {
		return talantsButtonsBuilder;
	}

	@Override
	public CharacteristicButton characteristic() {
		return characteristicButton;
	}

	@Override
	public AttackButton attack() {
		return attackButton;
	}

	@Override
	public CharacterPoolButton characterPool() {
		return characterPoolButton;
	}
}

