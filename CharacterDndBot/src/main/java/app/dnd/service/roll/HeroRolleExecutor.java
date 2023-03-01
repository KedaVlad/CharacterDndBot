package app.dnd.service.roll;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import app.bot.model.act.Act;
import app.bot.model.act.SingleAct;
import app.bot.model.act.actions.RollAction;
import app.bot.model.user.User;
import app.dnd.service.Executor;
import app.dnd.service.logic.characteristic.StatDiceBuilder;
import app.dnd.service.logic.proficiencies.ProficienciesDiceInitializer;
import app.dnd.util.math.Formula;

@Service
public class HeroRolleExecutor implements Executor<RollAction>{

	@Autowired
	private HeroRolleFormalizer heroRolleFormalizer;
	@Override
	public Act executeFor(RollAction action, User user) {
		Formula roll = heroRolleFormalizer.buildFormula(action, user);
		return SingleAct.builder()
				.name("deadEnd")
				.text(roll.execute())
				.build();
	}

	
}

@Component
class HeroRolleFormalizer {

	@Autowired
	private StatDiceBuilder statDiceBuilder;
	@Autowired
	private ProficienciesDiceInitializer proficienciesDiceInitializer;

	public Formula buildFormula(RollAction action, User user) {
		Formula answer = new Formula("ROLL",action.getBase());
		if(action.getDepends() != null) answer.addDicesToEnd(statDiceBuilder.build(user.getId(), action.getDepends()));
		if(action.isProficiency()) answer.addDicesToEnd(proficienciesDiceInitializer.init(user.getId(), action.getProficiency()));
		return answer;
	}

	
}