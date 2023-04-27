package app.dnd.service.roll;

import app.dnd.util.math.Dice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.actions.Action;
import app.dnd.model.actions.PreRoll;
import app.dnd.model.actions.RollAction;
import app.dnd.service.ability.logic.StatLogic;
import app.dnd.service.talants.logic.ProficienciesLogic;
import app.dnd.util.math.Formula;
import app.player.model.enums.Button;
import app.bot.model.user.ActualHero;

public interface RollLogic {
	Formula buildFormula(ActualHero actualHero, RollAction action);
	Action compleatRoll(ActualHero actualHero, RollAction action);
	Action compleatPreRoll(ActualHero actualHero, PreRoll action);
}

@Component
class HeroRolleFormalizer implements RollLogic {

	@Autowired
	private StatLogic statLogic;
	@Autowired
	private ProficienciesLogic proficienciesLogic;

	
	
	@Override
	public Formula buildFormula(ActualHero actualHero, RollAction action) {
		
		Formula formula = new Formula("ROLL",action.getBase().toArray(Dice[]::new));
		if(action.getDepends() != null) {
			formula.addDicesToEnd(statLogic.buildDice(actualHero, action.getDepends()));
		}
		if(action.isProficiency()) {
			formula.addDicesToEnd(proficienciesLogic.buildDice(actualHero, action.getProficiency()));
		}
		return formula;
	}

	@Override
	public Action compleatRoll(ActualHero actualHero, RollAction action) {
		return Action.builder().text(buildFormula(actualHero, action).execute()).build();
	}

	@Override
	public Action compleatPreRoll(ActualHero actualHero, PreRoll action) {
		
		Formula formula = buildFormula(actualHero, action.getRoll());
		String status = action.getStatus();
		String text = "Error";
		if (status.equals(Button.ADVANTAGE.NAME)) {
			text = formula.execute(true);
		} else if (status.equals(Button.DISADVANTAGE.NAME)) {
			text = formula.execute(false);
		} else if (status.equals(Button.BASIC.NAME)) {
			text = formula.execute();
		}
		
		Action answer = Action.builder().build();
		
		if (formula.isNatural1()) {
			answer.setText(text + "\n" + Button.CRITICAL_MISS.NAME + "!!! Good Luck next time... ");
			answer.setAnswers(new String[] {Button.CRITICAL_MISS.NAME});
	
		} else if (formula.isNatural20()) {
			answer.setText(text + "\n" + Button.CRITICAL_HIT.NAME + "!!!");
			answer.setAnswers(new String[] {Button.CRITICAL_HIT.NAME});
			answer.setAnswers(new String[] {Button.CRITICAL_MISS.NAME});
		} else {
			answer.setText(text);
		}
		return answer;
	}

	
}