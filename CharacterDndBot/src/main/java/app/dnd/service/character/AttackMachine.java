package app.dnd.service.character;

import org.springframework.stereotype.Service;

import app.bot.model.act.Act;
import app.bot.model.act.SingleAct;
import app.bot.model.act.actions.Action;
import app.bot.model.user.User;
import app.dnd.service.Executor;

@Service
public class AttackMachine implements Executor<Action>{

	@Override
	public Act executeFor(Action action, User user) {
		
		return SingleAct.builder().name("DeadEnd").text("this block is under development").build();
	}

}
