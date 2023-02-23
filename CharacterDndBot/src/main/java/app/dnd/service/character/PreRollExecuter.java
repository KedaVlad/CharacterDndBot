package app.dnd.service.character;

import org.springframework.stereotype.Service;

import app.bot.model.act.Act;
import app.bot.model.act.SingleAct;
import app.bot.model.act.actions.PreRoll;
import app.bot.model.user.User;
import app.dnd.service.Executor;

@Service
public class PreRollExecuter implements Executor<PreRoll> {

	@Override
	public Act executeFor(PreRoll action, User user) {
		
		return SingleAct.builder().name("DeadEnd").text("this block is under development").build();
	}

}
