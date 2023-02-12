package com.dnd.CharacterDndBot.service.dndTable.dndService.factoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dnd.CharacterDndBot.service.acts.Act;
import com.dnd.CharacterDndBot.service.acts.ReturnAct;
import com.dnd.CharacterDndBot.service.acts.SingleAct;
import com.dnd.CharacterDndBot.service.acts.actions.Action;
import com.dnd.CharacterDndBot.service.bot.user.User;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.CharacterDnd;
import com.dnd.CharacterDndBot.service.dndTable.dndService.Executor;
import com.dnd.CharacterDndBot.service.dndTable.dndService.Location;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CharacterFactory implements Executor<Action> {

	@Autowired
	private CharacterStartCreate characterStartCreate;
	@Autowired
	private CharacterApruveName characterApruveName;
	@Autowired
	private CharacterFinishCreate characterFinishCreate;
	
	@Override
	public Act executeFor(Action action, User user) {
		switch (action.condition()) {
		case 0:
			return characterStartCreate.executeFor(action, user);
		case 1:
			return characterApruveName.executeFor(action, user);
		case 2:
			return characterFinishCreate.executeFor(action, user);
		}
		log.error("CharacterFactory: out of bounds condition");
		return null;
	}
}

@Component
class CharacterStartCreate implements Executor<Action> {

	@Override
	public Act executeFor(Action action, User user) {
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
}

@Component
class CharacterApruveName implements Executor<Action> {

	@Override
	public Act executeFor(Action action, User user) {
		action.setButtons(new String[][] {{ "Yeah, right" }});
		return SingleAct.builder()
				.name("apruveName")
				.text("So, can I call you - " + action.getAnswers()[0] + "? If not, repeat your name.")
				.action(action)
				.build();
	}
}

@Component
class CharacterFinishCreate implements Executor<Action> {

	@Autowired
	private RaceFactory raceFactory;
	
	@Override
	public Act executeFor(Action action, User user) {
		user.getCharactersPool().setActual(new CharacterDnd(action.getAnswers()[0]));
		return raceFactory.executeFor(Action.builder().build(),user);
	}
}
