package app.dnd.service.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import app.dnd.dto.CharacterDnd;
import app.dnd.dto.Informator;
import app.dnd.dto.characteristics.Hp;
import app.dnd.dto.characteristics.Lvl;
import app.dnd.dto.stuffs.Stuff;
import app.dnd.dto.stuffs.items.Armor.Armors;
import app.dnd.service.ButtonName;
import app.dnd.service.logic.characteristic.StatModificator;
import app.dnd.service.logic.lvl.LvlAddExperience;
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
				+"\n" + ButtonName.CARRYING_STUFF_B +" (" + stuff.getPrepeared().size()+")";

	}

}

@Component
class CharacterInformator implements InformatorExecutor<CharacterDnd> {

	@Autowired
	private LvlInformator lvlInformator;
	@Autowired
	private HpInformator hpInformator;
	@Autowired
	private AcInformator acInformator;

	@Override
	public String getInformation(CharacterDnd characterDnd) {
		String answer = characterDnd.getName() + "\n\n"		
				+ "Race: " + characterDnd.getRace().getRaceName() + " (" + characterDnd.getRace().getSubRace() + ")\r\n"
				+ "Class: " + characterDnd.getDndClass().get(0).getClassName() + " (" + characterDnd.getDndClass().get(0).getArchetype() + ")\r\n"
				+ "PROFICIENCY BONUS : "+ characterDnd.getAbility().getProficiencies().getProficiency() 
				+ "\r\n"+ lvlInformator.getInformation(characterDnd.getLvl())
				+ "\r\n" + hpInformator.getInformation(characterDnd.getHp()) 
				+ "\r\n" + acInformator.getInformation(characterDnd)
				+ "\r\n" + "SPEED: "+characterDnd.getSpeed();


		return answer;

	}
}

@Component
class HpInformator implements InformatorExecutor<Hp> {

	@Override
	public String getInformation(Hp hp) {
		String answer = "HP: " + hp.getNow();
		if (hp.getTimeHp() > 0) {
			answer += "[+" + hp.getTimeHp() + "]";
		} 
		answer += "|" + hp.getMax();
		if (hp.isDead()) {
			answer += " (DEAD)";
		} else if (hp.isCknoked()) {
			answer += " (cknoked)";
		}
		return answer;
	}
}

@Component
class LvlInformator implements InformatorExecutor<Lvl> {

	@Autowired
	private LvlAddExperience lvlAddExperience;

	@Override
	public String getInformation(Lvl lvl) {
		String answer;
		if (lvl.getLvl() == 20) {
			answer = "LVL: 20(MAX LVL)";
		} else {
			answer = "LVL: " + lvl.getLvl() + "(" + lvl.getExperience() + "|" + lvlAddExperience.getExpPerLvl()[lvl.getLvl()]+ ")";
		}
		return answer + "\n";
	}
}

@Component
class AcInformator implements InformatorExecutor<CharacterDnd> {

	@Autowired
	private StatModificator statModificator;

	@Override
	public String getInformation(CharacterDnd characterDnd) {

		Stuff stuff = characterDnd.getStuff();
		String answer = "AC: ";
		if (stuff.getWeared()[0] == null) {
			//	if (barbarian) {
			//		answer += (10 + stats[2].getModificator() + stats[1].getModificator());
			//	} else {
			answer += (10 + statModificator.modificate(characterDnd.getCharacteristics().getStats()[1]));
			//	}
		} else {
			Armors type = stuff.getWeared()[0].getType();
			int armor;
			if (type.getStatDependBuff() > type.getBaseArmor()) {
				armor = type.getBaseArmor() + statModificator.modificate(characterDnd.getCharacteristics().getStats()[1]);
				if (armor > type.getStatDependBuff()) {
					answer += type.getStatDependBuff();
				} else {
					answer += armor;
				}
			} else {
				armor = type.getBaseArmor();
			}
		}
		if (stuff.getWeared()[1] == null) {
			return answer;
		} else {
			return answer += "(+" + stuff.getWeared()[1].getType().getBaseArmor() + ")";
		}



	}
}
