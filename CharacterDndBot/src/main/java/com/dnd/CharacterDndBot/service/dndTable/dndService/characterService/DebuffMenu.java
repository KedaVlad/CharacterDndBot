package com.dnd.CharacterDndBot.service.dndTable.dndService.characterService;

import com.dnd.CharacterDndBot.service.acts.Act;
import com.dnd.CharacterDndBot.service.acts.ReturnAct;
import com.dnd.CharacterDndBot.service.acts.SingleAct;
import com.dnd.CharacterDndBot.service.acts.actions.Action;
import com.dnd.CharacterDndBot.service.bot.user.User;
import com.dnd.CharacterDndBot.service.dndTable.dndService.Executor;
import com.dnd.CharacterDndBot.service.dndTable.dndService.Location;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DebuffMenu extends Executor<Action> {

	public DebuffMenu(Action action) {
		super(action);
	}

	@Override
	public Act executeFor(User user) {
		int condition = 0;
		if (action.getAnswers() != null) condition = action.getAnswers().length;
		switch (condition) {
		case 0:
			return debuff();
		case 1:
			return addDebuff(user);
		}
		log.error("DebuffMenu: out of bounds condition");
		return null;
	}

	private Act addDebuff(User user) {
		String name = "Debuff";
		for(int i = 0; i < user.getCharactersPool().cloudsValue(); i++)
		{
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

	private Act debuff() 
	{
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
