package app.dnd.service.inerComand;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.ObjectDnd;
import app.dnd.model.ability.Ability;
import app.dnd.model.ability.Hp;
import app.dnd.model.ability.SaveRoll;
import app.dnd.model.ability.Skill;
import app.dnd.model.ability.Stat;
import app.dnd.model.actions.Action;
import app.dnd.model.commands.CloudCommand;
import app.dnd.model.commands.CommandContainer;
import app.dnd.model.stuffs.Stuff;
import app.dnd.model.stuffs.items.Items;
import app.dnd.model.telents.attacks.AttackAbility;
import app.dnd.model.telents.attacks.AttackModification;
import app.dnd.model.telents.proficiency.Possession;
import app.dnd.model.telents.proficiency.Proficiencies;
import app.dnd.model.telents.spells.MagicSoul;
import app.dnd.model.telents.spells.Spell;
import app.dnd.service.ability.AbilityService;
import app.dnd.service.attack.AttackAbilityService;
import app.dnd.service.hp.HpService;
import app.dnd.service.stuff.StuffService;
import app.dnd.service.talants.MagicSoulService;
import app.dnd.service.talants.ProficienciesService;
import app.player.model.act.CloudAct;
import app.bot.model.user.ActualHero;

@Component
public class InerComandExecutor {

	@Autowired
	private HpService hpService;
	@Autowired
	private AbilityService abilityService;
	@Autowired
	private ProficienciesService proficienciesService;
	@Autowired
	private StuffService stuffService;
	@Autowired
	private MagicSoulService magicSoulService;
	@Autowired
	private AttackAbilityService attackAbilityService;

	public void execute(ActualHero actualHero, CommandContainer commandContainer) {

		compleatUp(actualHero, commandContainer.getUp());
		cloud(actualHero, commandContainer.getCloud());
		compleatAdd(actualHero, commandContainer.getAdd());
	}


	private void compleatUp(ActualHero actualHero, List<ObjectDnd> list) {

		Hp hp = hpService.findByIdAndOwnerName(actualHero.getId(), actualHero.getName());
		Ability ability = abilityService.findByIdAndOwnerName(actualHero.getId(), actualHero.getName());

		for(ObjectDnd object: list) {
			if(object instanceof Hp) {
				hp.setHpBonus(hp.getHpBonus() + ((Hp)object).getHpBonus());
			} else if(object instanceof Stat) {
				ability.getStats().get(((Stat) object).getCore()).up(((Stat) object).getValue());
			} else if(object instanceof Skill) {
				ability.getSkills().get(((Skill) object).getCore()).setProficiency(((Skill) object).getProficiency());
			} else if(object instanceof SaveRoll) {
				ability.getSaveRolls().get(((SaveRoll) object).getCore()).setProficiency(((SaveRoll) object).getProficiency());
			}
		}

		hpService.save(hp);
		abilityService.save(ability);
	}

	public void compleatAdd(ActualHero actualHero, List<ObjectDnd> list) {

		Proficiencies proficiensies = proficienciesService.findByIdAndOwnerName(actualHero.getId(), actualHero.getName());
		Stuff stuff = stuffService.findByIdAndOwnerName(actualHero.getId(), actualHero.getName());
		MagicSoul magicSoul = magicSoulService.findByIdAndOwnerName(actualHero.getId(), actualHero.getName());
		AttackAbility attackAbility = attackAbilityService.findByIdAndOwnerName(actualHero.getId(), actualHero.getName());

		for(ObjectDnd object: list) {
			if(object instanceof Possession) {
				proficiensies.getPossessions().add((Possession) object);
			} else if(object instanceof Items) {
				stuff.getInsideBag().add((Items)object);
			} else if (object instanceof Spell) {
				Spell target = (Spell) object;
				if (target.getLvlSpell() == 0) {
					magicSoul.getPoolCantrips().add(target);
				} else {
					magicSoul.getPoolSpells().add(target);
				}

			} else if (object instanceof AttackModification) {

				AttackModification target = (AttackModification) object;
				if (target.isPostAttack()) {
					if (attackAbility.getAfterAttak().contains(target)) {
						attackAbility.getAfterAttak().remove(target);
					}
					attackAbility.getAfterAttak().add(target);
				} else if (target.isPermanent()) {
					if (attackAbility.getPermanent().contains(target)) {
						attackAbility.getPermanent().remove(target);
					}
					attackAbility.getPermanent().add(target);
				} else {
					if (attackAbility.getPreAttacks().contains(target)) {
						attackAbility.getPreAttacks().remove(target);
					}
					attackAbility.getPreAttacks().add(target);
				}
			} else if (object instanceof MagicSoul) {
				MagicSoul target = (MagicSoul) object;
				magicSoul.marge(target);
			}
		} 

	proficienciesService.save(proficiensies);
	stuffService.save(stuff);
	magicSoulService.save(magicSoul);
	attackAbilityService.save(attackAbility);
}

public void cloud(ActualHero actualHero, List<CloudCommand> cloudCommands) {
	for(CloudCommand cloudCommand : cloudCommands) {
		actualHero.getHeroCloud().getClouds().add(CloudAct.builder()
				.name(cloudCommand.getName())
				.stage(Action.builder()
						.text(cloudCommand.getText())
						.build())
				.build());
	}
}

}


