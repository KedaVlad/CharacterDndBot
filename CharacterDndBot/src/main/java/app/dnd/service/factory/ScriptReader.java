package app.dnd.service.factory;

import org.springframework.stereotype.Service;

import app.bot.model.act.SingleAct;
import app.bot.model.act.actions.Action;
import app.dnd.dto.CharacterDnd;
import app.dnd.dto.ObjectDnd;
import app.dnd.dto.ability.attacks.AttackModification;
import app.dnd.dto.ability.features.Feature;
import app.dnd.dto.ability.features.InerFeature;
import app.dnd.dto.ability.proficiency.Possession;
import app.dnd.dto.ability.spells.MagicSoul;
import app.dnd.dto.ability.spells.Spell;
import app.dnd.dto.characteristics.Skill;
import app.dnd.dto.comands.AddComand;
import app.dnd.dto.comands.CloudComand;
import app.dnd.dto.comands.InerComand;
import app.dnd.dto.comands.UpComand;
import app.dnd.dto.stuffs.items.Items;

@Service
public class ScriptReader {

	private CloudExecutor cloudExecutor;
	private UpComandExecutor upComandExecutor;
	private AddComandExecutor addComandExecutor;

	public ScriptReader() {
		cloudExecutor = new CloudExecutor();
		upComandExecutor = new UpComandExecutor();
		addComandExecutor = new AddComandExecutor();
	}

	public void execute(CharacterDnd character, InerComand comand) {
		if (comand instanceof AddComand) {
			addComandExecutor.add(character, (AddComand) comand);
		} else if (comand instanceof UpComand) {
			upComandExecutor.up(character, (UpComand) comand);
		} else if (comand instanceof CloudComand) {
			cloudExecutor.cloud(character, (CloudComand) comand);
		}
	}


	class CloudExecutor {

		public void cloud(CharacterDnd character, CloudComand comand) {
			character.getClouds().add(SingleAct.builder()
					.name(comand.getName())
					.text(comand.getText())
					.action(Action.builder()
							.cloud()
							.build())
					.build());
		}
	}


	class UpComandExecutor {

		public void up(CharacterDnd character, UpComand comand) {

		}
	}


	class AddComandExecutor {

		private void add(CharacterDnd character, AddComand comand) {
			ObjectDnd[] objects = comand.getTargets();
			for (ObjectDnd object : objects) {
				if (object instanceof Items) {
					character.getStuff().getInsideBag().add((Items) object);
					break;
				} else if (object instanceof Feature) {
					character.getAbility().getFeatures().add((Feature) object);
					if (object instanceof InerFeature) {
						InerFeature target = (InerFeature) object;
						for (InerComand inerComand : target.getComand()) {
							execute(character, inerComand);
						}
					}
					break;
				} else if (object instanceof Possession) {
					Possession target = (Possession) object;
					if (target.getName().matches("^SR.*")) {
						for (Skill article : character.getCharacteristics().getSaveRolls()) {
							if (article.getName().equals(target.getName())) {
								article.setProficiency(target.getProf());
								break;
							}
						}
					} else {
						for (Skill article : character.getCharacteristics().getSkills()) {
							if (article.getName().equals(target.getName())) {
								article.setProficiency(target.getProf());
								break;
							}
						}
					}
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
					if (target.isPostAttack()) {
						if (character.getAttackMachine().getAfterAttak().contains(target)) {
							character.getAttackMachine().getAfterAttak().remove(target);
							character.getAttackMachine().getAfterAttak().add(target);
						} else {
							character.getAttackMachine().getAfterAttak().add(target);
						}
					} else if (target.isPermanent()) {
						if (character.getAttackMachine().getPermanent().contains(target)) {
							character.getAttackMachine().getPermanent().remove(target);
							character.getAttackMachine().getPermanent().add(target);
						} else {
							character.getAttackMachine().getPermanent().add(target);
						}
					} else {
						if (character.getAttackMachine().getPreAttacks().contains(target)) {
							character.getAttackMachine().getPreAttacks().remove(target);
							character.getAttackMachine().getPreAttacks().add(target);
						} else {
							character.getAttackMachine().getPreAttacks().add(target);
						}
					}
				}
			}
		}
	}
}