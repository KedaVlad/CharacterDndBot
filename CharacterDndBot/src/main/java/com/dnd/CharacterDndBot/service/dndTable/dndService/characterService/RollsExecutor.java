package com.dnd.CharacterDndBot.service.dndTable.dndService.characterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.dnd.CharacterDndBot.service.acts.Act;
import com.dnd.CharacterDndBot.service.acts.SingleAct;
import com.dnd.CharacterDndBot.service.acts.actions.Action;
import com.dnd.CharacterDndBot.service.bot.user.User;
import com.dnd.CharacterDndBot.service.dndTable.dndMath.Formalizer;
import com.dnd.CharacterDndBot.service.dndTable.dndService.Executor;
import com.dnd.CharacterDndBot.service.dndTable.dndService.Location;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RollsExecutor implements Executor<Action> {

	@Autowired
	private RollsMenu rollsMenu;
	@Autowired
	private CompleteCustomRoll completeCustomRoll;
	
	@Override
	public Act executeFor(Action action, User user) {
		int condition = 0;
		if (action.getAnswers() != null) condition = action.getAnswers().length;
		switch (condition) {
		case 0:
			return rollsMenu.executeFor(action, user);
		case 1:
			return completeCustomRoll.executeFor(action, user);
		}
		log.error("RollsMenu: out of bounds condition");
		return null;
	}
}

@Component
class RollsMenu implements Executor<Action> {

	@Override
	public Act executeFor(Action action, User user) {
		
		String text = "Choose a dice to throw, or write your own formula.\n"
				+ "To refer to a dice, use the D(or d) available dices you see in the console.\n"
				+ "For example: -d4 + 10 + 6d6 - 12 + d100";
		return SingleAct.builder()
				.name(ROLLS_B)
				.text(text)
				.action(Action.builder()
						.location(Location.ROLLS)
						.buttons(new String[][] {
							{"D4","D6","D8","D10"},
							{"D12","D20","D100"},
							{RETURN_TO_MENU}})
						.replyButtons()
						.mediator()
						.build())
				.build();
	}
}


@Component
class CompleteCustomRoll implements Executor<Action> {

	@Override
	public Act executeFor(Action action, User user) {

		String answer = action.getAnswers()[0];
		String text = Formalizer.formalize(answer);
		return SingleAct.builder()
				.name("DeadEnd")
				.text(text)
				.build();
	}
}