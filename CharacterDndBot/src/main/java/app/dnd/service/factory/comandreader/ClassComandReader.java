package app.dnd.service.factory.comandreader;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dnd.dto.CharacterDnd;
import app.dnd.dto.ObjectDnd;
import app.dnd.dto.ability.attacks.AttackModification;
import app.dnd.dto.ability.features.Feature;
import app.dnd.dto.ability.features.InerFeature;
import app.dnd.dto.ability.proficiency.Possession;
import app.dnd.dto.ability.spells.MagicSoul;
import app.dnd.dto.ability.spells.Spell;
import app.dnd.dto.comands.AddComand;
import app.dnd.dto.comands.CloudComand;
import app.dnd.dto.comands.InerComand;
import app.dnd.dto.comands.UpComand;

@Service
public class ClassComandReader {

	@Autowired
	private CloudExecutor cloudExecutor;
	@Autowired
	private UpComandExecutor upComandExecutor;


	public void execute(CharacterDnd character, InerComand comand) {
		if (comand instanceof AddComand) {
			add(character, (AddComand) comand);
		} else if (comand instanceof UpComand) {
			upComandExecutor.up(character, (UpComand) comand);
		} else if (comand instanceof CloudComand) {
			cloudExecutor.cloud(character, (CloudComand) comand);
		}
	}


	private void add(CharacterDnd character, AddComand comand) {
		ObjectDnd[] objects = comand.getTargets();
		for (ObjectDnd object : objects) {
			if(object instanceof InerComand) {
				execute(character, (InerComand) object);
			} else if (object instanceof Feature) {
				character.getAbility().getFeatures().add((Feature) object);
				if (object instanceof InerFeature) {
					InerFeature target = (InerFeature) object;
					for (InerComand inerComand : target.getComand()) {
						execute(character, inerComand);
					}
				}
			} else if (object instanceof Possession) {
				Possession target = (Possession) object;
				character.getAbility().getProficiencies().getPossessions().add(target);
			} else if (object instanceof MagicSoul) {
				MagicSoul target = (MagicSoul) object;
				character.getAbility().setMagicSoul(target);
			} else if (object instanceof Spell) {
				Spell target = (Spell) object;
				if (character.getAbility().getMagicSoul() != null) {
					if (target.getLvlSpell() == 0) {
						character.getAbility().getMagicSoul().getPoolCantrips().add(target);
					} else {
						character.getAbility().getMagicSoul().getPoolSpells().add(target);
					}
				}
			} else if (object instanceof AttackModification) {

				AttackModification target = (AttackModification) object;
				List<AttackModification> targetList;
				if (target.isPostAttack()) {
					targetList = character.getAttackMachine().getAfterAttak();

				} else if (target.isPermanent()) {
					targetList = character.getAttackMachine().getPermanent();
				} else {
					targetList = character.getAttackMachine().getPreAttacks();
				}

				for(int i = 0; i < targetList.size(); i++) {
					if(targetList.get(i).toString().equals(target.toString())) {
						targetList.remove(i);
						break;
					}
				}
				targetList.add(target);
			}
		}
	}
}