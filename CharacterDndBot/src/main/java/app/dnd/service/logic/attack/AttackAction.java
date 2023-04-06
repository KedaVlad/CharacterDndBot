package app.dnd.service.logic.attack;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.ability.attacks.AttackModification;
import app.dnd.model.actions.Action;
import app.dnd.model.actions.BaseAction;
import app.dnd.model.actions.PoolActions;
import app.dnd.model.actions.PreRoll;
import app.dnd.model.actions.RollAction;
import app.dnd.model.stuffs.items.Weapon;
import app.dnd.service.logic.ButtonLogic;
import app.dnd.service.logic.HeroLogic;
import app.dnd.service.logic.InformatorLogic;
import app.dnd.service.logic.characteristic.stat.StatDiceBuilder;
import app.dnd.service.logic.talants.prof.ProfDiceBuilder;
import app.dnd.util.math.Dice;
import app.dnd.util.math.Formula;
import app.player.model.Stage;
import app.player.model.enums.Button;
import app.player.model.enums.Location;

public interface AttackAction {

	BaseAction preAttack(Stage stage, Long id);
	BaseAction postAttack(Stage stage, Long id);
	BaseAction preHit(Stage stage, Long id);
	BaseAction postHit(Stage stage, Long id);
}

@Component
class AttackActor implements AttackAction {

	@Autowired
	private ButtonLogic buttonLogic;
	@Autowired
	private InformatorLogic informator;
	@Autowired
	private HeroLogic heroLogic;
	@Autowired
	private HeroRolleFormalizer heroRolleFormalizer;

	@Override
	public BaseAction preAttack(Stage stage, Long id) {

		Action action = (Action) stage;
		Weapon weapon = (Weapon) action.getObjectDnd();
		return PoolActions.builder()
				.text(informator.attack().preAttack(weapon))
				.actionsPool(buttonLogic.attack().preAttak(weapon, id))
				.build();
	}

	@Override
	public BaseAction postAttack(Stage stage, Long id) {

		Action action = (Action) stage;
		
		
		AttackModification attack = (AttackModification) action.getObjectDnd();
		heroLogic.attack().setAttack(attack, id);
		return PreRoll.builder()
				.location(Location.ATTACK_MACHINE)
				.text(attack.toString())
				.roll(RollAction.buider()
						.diceCombo(attack.getAttack().toArray(Dice[]::new))
						.proficiency(heroLogic.attack().prof(attack, id))
						.statDepend(attack.getStatDepend())
						.build())
				.build();
	}

	@Override
	public BaseAction preHit(Stage stage, Long id) {

		PreRoll preRoll = (PreRoll) stage;
		Formula formula = heroRolleFormalizer.buildFormula(preRoll.getRoll(), id);
		String status = preRoll.getStatus();
		String text = "Error";
		if (status.equals(Button.ADVANTAGE.NAME)) {
			text = formula.execute(true);
		} else if (status.equals(Button.DISADVANTAGE.NAME)) {
			text = formula.execute(false);
		} else if (status.equals(Button.BASIC.NAME)) {
			text = formula.execute();
		}

		BaseAction[][] pool = null;
		if (formula.isNatural1()) {
			text += "\n" + Button.CRITICAL_MISS.NAME + "!!! Good Luck next time... ";
		} else if (formula.isNatural20()) {
			text += "\n" + Button.CRITICAL_HIT.NAME;
			pool = buttonLogic.attack().crit(id);
		} else {
			pool = buttonLogic.attack().hit(id);
		}

		return PoolActions.builder()
				.actionsPool(pool)
				.text(text)
				.build();
	}

	@Override
	public BaseAction postHit(Stage stage, Long id) {
		if(stage instanceof RollAction) {
		RollAction action = (RollAction) stage;
		return Action.builder().text(heroRolleFormalizer.buildFormula(action, id).execute()).build();
		} else {
			return Action.builder().text("GOODLUCK NEXT TIME").build();
		}
	}
}

@Component
class HeroRolleFormalizer {

	@Autowired
	private StatDiceBuilder statDiceBuilder;
	@Autowired
	private ProfDiceBuilder profDiceBuilder;

	public Formula buildFormula(RollAction action, Long id) {
		
		Formula formula = new Formula("ROLL",action.getBase());
		if(action.getDepends() != null) formula.addDicesToEnd(statDiceBuilder.build(id, action.getDepends()));
		if(action.isProficiency()) formula.addDicesToEnd(profDiceBuilder.build(id, action.getProficiency()));
		return formula;
	}

	
}
