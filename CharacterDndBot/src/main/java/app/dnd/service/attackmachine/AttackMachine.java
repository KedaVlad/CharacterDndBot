package app.dnd.service.attackmachine;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import app.bot.model.ActualHero;
import app.bot.model.act.Act;
import app.bot.model.act.ReturnAct;
import app.bot.model.act.SingleAct;
import app.bot.model.act.actions.Action;
import app.bot.model.act.actions.BaseAction;
import app.bot.model.act.actions.PoolActions;
import app.bot.model.act.actions.PreRoll;
import app.bot.model.act.actions.RollAction;
import app.bot.model.user.User;
import app.bot.service.ActualHeroService;
import app.dnd.dto.CharacterDnd;
import app.dnd.dto.ability.attacks.AttackAbility;
import app.dnd.dto.ability.attacks.AttackModification;
import app.dnd.dto.ability.proficiency.Proficiencies.Proficiency;
import app.dnd.dto.stuffs.items.Weapon;
import app.dnd.dto.stuffs.items.Weapon.WeaponProperties;
import app.dnd.service.Executor;
import app.dnd.service.Location;
import app.dnd.service.logic.proficiencies.ProficienciesFinder;
import app.dnd.util.math.Dice;
import app.dnd.util.math.Formalizer.Roll;

@Service
public class AttackMachine implements Executor<Action>{


	@Autowired
	private StartAttack startAttack;
	@Autowired
	private MakeAttack makeAttack;
	@Autowired
	private PostAttack postAttack;

	@Override
	public Act executeFor(Action action, User user) {

		if (action.getObjectDnd() == null) {

			if (action.condition() == 0) {
				return SingleAct.builder().name("MISS").text("GOODLUCK NEXT TIME").build();
			} else {
				return postAttack.executeFor(action, user);
			}
		} else {
			if (action.getObjectDnd() instanceof Weapon) {
				return startAttack.executeFor(action, user);
			} else if (action.getObjectDnd() instanceof AttackModification) {
				return makeAttack.executeFor(action, user);
			} else {
				return ReturnAct.builder().target(MENU_B).build();
			}
		}
	}
}

@Component
class StartAttack implements Executor<Action> {

	@Autowired
	private ActualHeroService actualHeroService;
	@Autowired
	private SubAttackBuilder subAttackBuilder;

	@Override
	public Act executeFor(Action action, User user) {

		Weapon weapon = (Weapon) action.getObjectDnd();
		ActualHero actualHero = actualHeroService.getById(user.getId());
		actualHero.getCharacter().getAttackMachine().setTargetWeapon(weapon);
		actualHeroService.save(actualHero);
		List<AttackModification> attacks = buildAttack(actualHero.getCharacter().getAttackMachine());
		BaseAction[][] pool = new BaseAction[attacks.size()][1];
		for (int i = 0; i < attacks.size(); i++) {
			AttackModification target = attacks.get(i);
			pool[i][0] = Action.builder().location(Location.ATTACK_MACHINE).name(target.getName()).objectDnd(target).build();
		}
		return SingleAct.builder()
				.name(weapon.getName())
				.text(weapon.getDescription())
				.action(PoolActions.builder()
						.actionsPool(pool)
						.build())
				.build();
	}

	private List<AttackModification> buildAttack(AttackAbility attackAbility) {
		List<AttackModification> answer = new ArrayList<>();
		Weapon weapon = attackAbility.getTargetWeapon();
		AttackModification base = new AttackModification();
		base.setName("Base attack");
		if (weapon.getAttack() > 0) {
			base.getAttack().add(new Dice("Weapon buff", weapon.getAttack(), Roll.NO_ROLL));
		}
		if (weapon.getDamage() > 0) {
			base.getAttack().add(new Dice("Weapon buff", weapon.getDamage(), Roll.NO_ROLL));
		}
		for (AttackModification type : weapon.getType().getAttackTypes()) {
			AttackModification target = type.marger(base);
			target = permanentBuff(attackAbility, target);
			answer.addAll(subAttackBuilder.build(target, attackAbility.getPreAttacks()));

		}
		return answer;

	}

	private AttackModification permanentBuff(AttackAbility attackAbility, AttackModification attack) {
		for (AttackModification type : attackAbility.getPermanent()) {
			int condition = type.getRequirement().length;
			for (WeaponProperties properties : type.getRequirement()) {
				if (condition == 0) {
					attack = attack.marger(type);
					break;
				} else {
					for (WeaponProperties need : attack.getRequirement()) {
						if (properties.equals(need)) {
							condition--;
							break;
						}
					}
				}
			}
		}
		return attack;
	}
}

@Component
class SubAttackBuilder {

	public List<AttackModification> build(AttackModification attack, List<AttackModification> attacks) {
		List<AttackModification> answer = new ArrayList<>();
		answer.add(attack);
		for (AttackModification type : attacks) {
			int condition = type.getRequirement().length;
			String typeAttak = "|";
			for (WeaponProperties properties : type.getRequirement()) {
				typeAttak += properties.toString() + "|";
				for (WeaponProperties need : attack.getRequirement()) {
					if (properties.equals(need)) {
						condition--;
						break;
					}
				}
			}
			if (condition == 0) {
				AttackModification compleat = attack.marger(type);
				compleat.setName(type.getName() + typeAttak);
				answer.add(compleat);
			}
		}

		return answer;
	}
}

@Component
class MakeAttack implements Executor<Action> {

	@Autowired
	private ProficienciesFinder proficienciesFinder;
	@Autowired
	private ActualHeroService actualHeroService;

	@Override
	public Act executeFor(Action action, User user) {
		ActualHero actualHero = actualHeroService.getById(user.getId());
		CharacterDnd characterDnd = actualHero.getCharacter();
		AttackAbility attackAbility = characterDnd.getAttackMachine();
		AttackModification attack = (AttackModification) action.getObjectDnd();
		attackAbility.setTargetAttack(attack);
		actualHeroService.save(actualHero);
		return SingleAct.builder()
				.name(attack.getName())
				.text(attack.toString()).action(
						PreRoll.builder()
						.location(Location.ATTACK_MACHINE)
						.roll(RollAction.buider()
								.diceCombo(attack.getAttack().toArray(Dice[]::new))
								.proficiency(buildProf(characterDnd))
								.statDepend(attack.getStatDepend()).build())
						.build())
				.build();
	}

	private Proficiency buildProf(CharacterDnd characterDnd) {

		if (proficienciesFinder.check(characterDnd.getAbility().getProficiencies(), "Military Weapon") //WeaponProperties.MILITARY.toString())
				|| (simpleCheck(characterDnd.getAttackMachine().getTargetWeapon()) 
				&& proficienciesFinder.check(characterDnd.getAbility().getProficiencies(), "Simple Weapon") //WeaponProperties.SIMPLE.toString()))
				|| proficienciesFinder.check(characterDnd.getAbility().getProficiencies(), characterDnd.getAttackMachine().getTargetWeapon().getType().toString()))) {
			return Proficiency.BASE;
		}
		return null;
	}

	private boolean simpleCheck(Weapon weapon) {
		for (WeaponProperties properties : weapon.getType().getAttackTypes()[0].getRequirement()) {
			if (properties.equals(WeaponProperties.SIMPLE))
				return true;
		}
		return false;
	}
}

@Component
class PostAttack  implements Executor<Action> {

	@Autowired
	private SubAttackBuilder subAttackBuilder;
	@Autowired
	private ActualHeroService actualHeroService;

	@Override
	public Act executeFor(Action action, User user) {

		if (action.condition() == 1) {
			return makeHit(actualHeroService.getById(user.getId()).getCharacter() ,action);
		} else {
			return makeCrit(actualHeroService.getById(user.getId()).getCharacter() ,action);
		}
	}

	private Act makeHit(CharacterDnd characterDnd, Action action) {

		AttackAbility attackAbility = characterDnd.getAttackMachine();
		AttackModification attack = characterDnd.getAttackMachine().getTargetAttack();
		List<AttackModification> attacks = subAttackBuilder.build(attack, attackAbility.getAfterAttak());
		BaseAction[][] nextSteps = new BaseAction[attacks.size() + 1][1];
		for (int i = 0; i < attacks.size(); i++) {
			AttackModification target = attacks.get(i);
			nextSteps[i][0] = RollAction.buider().name(target.getName())
					.diceCombo(target.getDamage().toArray(Dice[]::new)).statDepend(target.getStatDepend()).build();
		}
		nextSteps[nextSteps.length - 1][0] = Action.builder().location(Location.ATTACK_MACHINE).name("MISS").build();
		return SingleAct.builder()
				.name("makeHit")
				.text(action.getAnswers()[0])
				.action(PoolActions.builder().actionsPool(nextSteps).build())
				.build();
	}

	private Act makeCrit(CharacterDnd characterDnd, Action action) {
		AttackAbility attackAbility = characterDnd.getAttackMachine();
		AttackModification attack = characterDnd.getAttackMachine().getTargetAttack();
		String crit = action.getAnswers()[1];
		if (crit.equals(CRITICAL_MISS)) {
			return SingleAct.builder().name("criticalMiss").text(action.getAnswers()[0] + "\nGOODLUCK NEXT TIME").build();
		} else {
			List<AttackModification> attacks = subAttackBuilder.build(crit(attackAbility, attack), attackAbility.getAfterAttak());
			BaseAction[][] nextSteps = new BaseAction[attacks.size()][1];
			for (int i = 0; i < attacks.size(); i++) {
				AttackModification target = attacks.get(i);
				nextSteps[i][0] = RollAction.buider().name(target.getName())
						.diceCombo((Dice[]) target.getDamage().toArray()).statDepend(target.getStatDepend()).build();
			}

			return SingleAct.builder().name("makeCrit").text(action.getAnswers()[0])
					.action(PoolActions.builder().actionsPool(nextSteps).build()).build();
		}
	}

	private AttackModification crit(AttackAbility attackAbility, AttackModification attack) {
		Dice damage = attack.getDamage().get(0);
		Roll roll = damage.getCombo()[0];
		List<Roll> critsRolls = new ArrayList<>();
		for (int i = 0; i < attackAbility.getCritX(); i++) {
			critsRolls.add(roll);
		}
		attack.getDamage().add(new Dice("Crit", 0, (Roll[]) critsRolls.toArray()));
		return attack;
	}
}
