package app.dnd.service.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dnd.service.logic.attack.AttackInfo;
import app.dnd.service.logic.characteristic.CharacteristicInfo;
import app.dnd.service.logic.talants.TalantsInfo;


public interface InformatorLogic {

	public TalantsInfo talants();
	public CharacteristicInfo characteristic();
	public AttackInfo attack();
	
}

@Service
class Informator implements InformatorLogic{

	@Autowired
	private TalantsInfo talantsInfo;
	@Autowired
	private CharacteristicInfo characteristicInfo;
	@Autowired
	private AttackInfo attackInfo;

	@Override
	public TalantsInfo talants() {
		return talantsInfo;
	}

	@Override
	public CharacteristicInfo characteristic() {
		return characteristicInfo;
	}

	@Override
	public AttackInfo attack() {
		return attackInfo;
	}
	
	
	
}


