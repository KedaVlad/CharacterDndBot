package app.dnd.service.character;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import app.bot.model.act.Act;
import app.bot.model.act.ReturnAct;
import app.bot.model.act.SingleAct;
import app.bot.model.act.actions.Action;
import app.bot.model.user.Clouds;
import app.bot.model.user.User;
import app.dnd.service.Executor;
import app.dnd.service.Location;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DebuffExecutor implements Executor<Action> {

	@Autowired
	private DebuffStartCreate debuffStartCreate;
	@Autowired
	private DebuffEndCreate debuffEndCreate;
	
	@Override
	public Act executeFor(Action action, User user) {
		int condition = 0;
		if (action.getAnswers() != null) condition = action.getAnswers().length;
		switch (condition) {
		case 0:
			return debuffStartCreate.executeFor(action, user);
		case 1:
			return debuffEndCreate.executeFor(action, user);
		}
		log.error("DebuffMenu: out of bounds condition");
		return null;
	}
}

@Component
class DebuffStartCreate implements Executor<Action> {

	@Override
	public Act executeFor(Action action, User user) {
		return ReturnAct.builder()
				.target(MENU_B)
				.act(SingleAct.builder()
						.name("Debuff")
						.text("(Write) What is effect on you? After it will end ELIMINATE this...")
						.action(Action.builder()
								.mediator()
								.replyButtons()
								.location(Location.DEBUFF)
								.buttons(new String[][] {{RETURN_TO_MENU}})
								.build())
						.build())
				.build();
	}

}

@Component
class DebuffEndCreate implements Executor<Action> {

	@Override
	public Act executeFor(Action action, User user) {
		
		Clouds clouds = user.getClouds();
		String name = "Debuff";
		for (int i = 0; i < clouds.cloudsValue(); i++) {
			name += "A";
		}
		return SingleAct.builder()
				.name(name)
				.text(action.getAnswers()[0])
				.action(Action.builder()
						.cloud()
						.build())
				.build();

	}
}