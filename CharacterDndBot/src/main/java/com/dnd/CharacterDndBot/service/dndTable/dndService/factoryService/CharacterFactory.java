package com.dnd.CharacterDndBot.service.dndTable.dndService.factoryService;

import com.dnd.CharacterDndBot.service.User;
import com.dnd.CharacterDndBot.service.acts.Act;
import com.dnd.CharacterDndBot.service.acts.ReturnAct;
import com.dnd.CharacterDndBot.service.acts.SingleAct;
import com.dnd.CharacterDndBot.service.acts.actions.Action;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.CharacterDnd;
import com.dnd.CharacterDndBot.service.dndTable.dndService.Executor;
import com.dnd.CharacterDndBot.service.dndTable.dndService.Location;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CharacterFactory extends Executor<Action> {
	
	public CharacterFactory(Action action) {
		super(action);
	}

	@Override
	public Act executeFor(User user) {
		int condition = 0;
		if (action.getAnswers() != null) condition = action.getAnswers().length;
		switch (condition) {
		case 0:
			return startCreate();
		case 1:
			return apruveName();
		case 2:
			return finish(user);
		}
		log.error("CharacterFactory: out of bounds condition");
		return null;
	}

	private Act startCreate() {
		return ReturnAct.builder()
				.act(SingleAct.builder()
						.name("CreateCharacter")
						.text("Traveler, how should I call you?!\n(Write Hero name)")
						.action(Action.builder()
								.location(Location.CHARACTER_FACTORY)
								.mediator()
								.build())
						.build())
				.target(START_B)
				.build();
	}

	private Act apruveName() {
		action.setButtons(new String[][] { { "Yeah, right" } });
		return SingleAct.builder()
				.name("apruveName")
				.text("So, can I call you - " + action.getAnswers()[0] + "? If not, repeat your name.")
				.action(action)
				.build();
	}

	private Act finish(User user) {
		user.getCharactersPool().setActual(new CharacterDnd(action.getAnswers()[0]));
		return new RaceFactory(Action.builder().build()).executeFor(user);
	}

}
