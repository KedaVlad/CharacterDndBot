package app.player.service.stage.event.character;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dnd.model.actions.Action;
import app.dnd.model.actions.PreRoll;
import app.player.model.act.Act;
import app.player.model.act.SingleAct;
import app.player.service.stage.Executor;
import app.user.model.User;

@Service
public class DefaultPreRollExecutor implements Executor<PreRoll> {

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


