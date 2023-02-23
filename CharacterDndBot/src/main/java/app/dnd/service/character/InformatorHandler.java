package app.dnd.service.character;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import app.dnd.dto.CharacterDnd;
import app.dnd.dto.Informator;
import app.dnd.dto.characteristics.Lvl;
import app.dnd.dto.stuffs.Stuff;
import app.dnd.service.ButtonName;
import app.dnd.service.factory.InformatorExecutor;
import app.dnd.service.logic.characteristic.StatModificator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class InformatorHandler {

	@Autowired
	private DefaultInformator defaultInformator;
	@Autowired
	private CharacterInformator characterInformator;
	@Autowired
	private StuffInformator stuffInformator;

	public String handle(Informator informator) {

		if (informator instanceof CharacterDnd) {
			return characterInformator.getInformation((CharacterDnd) informator);

		} else if (informator instanceof Stuff) {
			return stuffInformator.getInformation((Stuff) informator);

		} else {
			log.error("InformatorFactory: This object is not supported by informant");
			return defaultInformator.getInformation(informator);
		}
	}
}

@Component
class DefaultInformator implements InformatorExecutor<Informator> {

	@Override
	public String getInformation(Informator informator) {
		return "This object is not supported by informantor";
	}

}

@Component
class StuffInformator implements InformatorExecutor<Stuff> {

	@Override
	public String getInformation(Stuff stuff) {

		return "Here, you'll be able to access the hero`s "+ ButtonName.WALLET_B +", "+ButtonName.BAG_B+", and any items they have in their possession("+ButtonName.CARRYING_STUFF_B+")."
				+"\n"
				+"\n" + ButtonName.WALLET_B  + " "+ stuff.getWallet().toString() 
				+"\n" + ButtonName.BAG_B + " ("+ stuff.getInsideBag().size()+")"
				+"\n" + ButtonName.CARRYING_STUFF_B +" (" + stuff.getWallet().toString()+")";

	}

}

@Component
class CharacterInformator implements InformatorExecutor<CharacterDnd> {

	@Autowired
	private StatModificator statModificator;
	@Override
	public String getInformation(CharacterDnd informator) {
		String answer = informator.getName()		
				+ "Race: " + informator.getRace().getRaceName() + " (" + informator.getRace().getSubRace() + ")\r\n"
				+ "Class: " + informator.getDndClass().get(0).getClassName() + " (" + informator.getDndClass().get(0).getArchetype() + ")\r\n"
				+ "Level: " + informator.getLvl().getLvl() + " ( " + informator.getLvl().getExperience() + " | "+informator.getLvl().getExpPerLvl()[informator.getLvl().getLvl()]+" )\r\n"
				+ "\r\n"
				+ "PROFICIENCY BONUS : "+ informator.getAbility().getProficiencies().getProficiency() + "\r\n"
				+ "\r\n"
				+ "[ HP : "+informator.getHp().getNow()+" | "+informator.getHp().getMax() + " ] [ AC : "+(10 + statModificator.modificate(informator.getCharacteristics().getStats()[1])) + " ][ SPEED : 25 ]";


		return answer;

	}
}
