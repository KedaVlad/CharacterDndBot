package app.player.service.stage.event.factory.comandreader;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dnd.model.ObjectDnd;
import app.dnd.model.ability.attacks.AttackModification;
import app.dnd.model.ability.features.Feature;
import app.dnd.model.ability.features.InerFeature;
import app.dnd.model.ability.proficiency.Possession;
import app.dnd.model.ability.spells.MagicSoul;
import app.dnd.model.ability.spells.Spell;
import app.dnd.model.comands.AddComand;
import app.dnd.model.comands.CloudComand;
import app.dnd.model.comands.InerComand;
import app.dnd.model.comands.UpComand;
import app.dnd.model.hero.CharacterDnd;

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