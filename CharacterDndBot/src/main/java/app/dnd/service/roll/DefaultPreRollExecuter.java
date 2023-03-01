package app.dnd.service.roll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import app.bot.model.act.Act;
import app.bot.model.act.SingleAct;
import app.bot.model.act.actions.Action;
import app.bot.model.act.actions.PreRoll;
import app.bot.model.user.User;
import app.dnd.service.Executor;

@Service
public class DefaultPreRollExecuter implements Executor<PreRoll> {

	@Autowired
	private PostRoll postRoll;

	@Override
	public Act executeFor(PreRoll action, User user) {
		Action roll = postRoll.executeFor(action, user);
		return SingleAct.builder()
				.name("deadEnd")
				.text(roll.getAnswers()[0])
				.build();
	}
}


