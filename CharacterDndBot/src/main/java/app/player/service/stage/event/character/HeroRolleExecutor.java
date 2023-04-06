package app.player.service.stage.event.character;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import app.dnd.model.actions.RollAction;
import app.dnd.service.logic.characteristic.stat.StatDiceBuilder;
import app.dnd.service.logic.talants.prof.ProficienciesDiceInitializer;
import app.dnd.util.math.Formula;
import app.player.model.act.Act;
import app.player.model.act.SingleAct;
import app.player.service.stage.Executor;
import app.user.model.User;

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

