package app.dnd.service.attackmachine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.bot.model.act.Act;
import app.bot.model.act.actions.PreRoll;
import app.bot.model.user.User;
import app.dnd.service.Executor;
import app.dnd.service.roll.PostRoll;

@Component
public class AttackMachinePreRoll implements Executor<PreRoll>{

	@Autowired
	private AttackMachine attackMachine;
	@Autowired
	private PostRoll postRoll;
	@Override
	public Act executeFor(PreRoll action, User user) {
		
		return attackMachine.executeFor(postRoll.executeFor(action, user), user);
	}
}
