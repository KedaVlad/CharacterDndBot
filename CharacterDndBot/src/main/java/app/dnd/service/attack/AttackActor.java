package app.dnd.service.attack;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.actions.Action;
import app.dnd.model.actions.BaseAction;
import app.dnd.model.actions.PoolActions;
import app.dnd.model.actions.PreRoll;
import app.dnd.model.actions.RollAction;
import app.dnd.model.actions.SingleAction;
import app.dnd.model.stuffs.items.Weapon;
import app.dnd.model.telents.attacks.AttackModification;
import app.dnd.service.roll.RollLogic;
import app.dnd.util.math.Dice;
import app.player.model.Stage;
import app.player.model.enums.Button;
import app.player.model.enums.Location;
import app.bot.model.user.ActualHero;

@Component
public class AttackActor implements AttackAction {

	@Autowired
	private AttackButtonBuilder attackButtonBuilder;
	@Autowired
	private AttackLogic attackLogic;
	@Autowired
	private RollLogic rollLogic;

	@Override
	public BaseAction preAttack(ActualHero hero, Stage stage) {

		Weapon weapon = (Weapon)((Action) stage).getObjectDnd();
		return PoolActions.builder()
				.text(weapon.getDescription())
				.actionsPool(attackButtonBuilder.preAttak(attackLogic.buildAttack(hero, weapon)))
				.build();
	}

	@Override
	public BaseAction postAttack(ActualHero hero, Stage stage) {

		AttackModification attack = (AttackModification) ((Action) stage).getObjectDnd();
		attackLogic.setAttack(hero, attack);
		return PreRoll.builder()
				.location(Location.ATTACK_MACHINE)
				.text(attack.toString())
				.roll(RollAction.buider()
						.diceCombo(attack.getAttack().toArray(Dice[]::new))
						.proficiency(attackLogic.prof(hero, attack))
						.statDepend(attack.getStatDepend())
						.build())
				.build();
	}

	@Override
	public BaseAction preHit(ActualHero hero, Stage stage) {

		Action action = rollLogic.compleatPreRoll(hero, (PreRoll)stage);
		SingleAction[][] pool = null;
		if (action.condition() == 0) {
			pool = attackButtonBuilder.hit(attackLogic.hit(hero));
		} else if (action.getAnswers()[0].equals(Button.CRITICAL_HIT.NAME)) {
			pool = attackButtonBuilder.crit(attackLogic.crit(hero));
		}

		return PoolActions.builder()
				.actionsPool(pool)
				.text(action.getText())
				.build();
	}

	@Override
	public BaseAction postHit(ActualHero hero, Stage stage) {
		if(stage instanceof RollAction) {
		return rollLogic.compleatRoll(hero, (RollAction) stage);
		} else {
			return Action.builder().text("GOODLUCK NEXT TIME").build();
		}
	}
}

@Component
class AttackButtonBuilder  {

	public SingleAction[][] preAttak(List<AttackModification> attacks) {
		SingleAction[][] pool = new SingleAction[attacks.size()][1];
		for (int i = 0; i < attacks.size(); i++) {
			AttackModification target = attacks.get(i);
			pool[i][0] = Action.builder()
					.location(Location.ATTACK_MACHINE)
					.name(target.getName())
					.objectDnd(target)
					.build();
		}
		return pool;
	}

	public SingleAction[][] hit(List<AttackModification> attacks) {
		SingleAction[][] nextSteps = new SingleAction[attacks.size() + 1][1];
		for (int i = 0; i < attacks.size(); i++) {
			AttackModification target = attacks.get(i);
			nextSteps[i][0] = RollAction.buider()
					.name(target.getName())
					.location(Location.ATTACK_MACHINE)
					.diceCombo(target.getDamage().toArray(Dice[]::new))
					.statDepend(target.getStatDepend())
					.build();
		}
		nextSteps[nextSteps.length - 1][0] = Action.builder().location(Location.ATTACK_MACHINE).name("MISS").build();
		return nextSteps;
	}

	public SingleAction[][] crit(List<AttackModification> attacks) {
		SingleAction[][] nextSteps = new SingleAction[attacks.size()][1];
		for (int i = 0; i < attacks.size(); i++) {
			AttackModification target = attacks.get(i);
			nextSteps[i][0] = RollAction.buider()
					.name(target.getName())
					.location(Location.ATTACK_MACHINE)
					.diceCombo((Dice[]) target.getDamage().toArray())
					.statDepend(target.getStatDepend()).build();
		}

		return nextSteps;
	}
		
}

