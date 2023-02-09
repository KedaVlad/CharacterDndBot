package com.dnd.CharacterDndBot.service.dndTable.dndService.characterService;

import com.dnd.CharacterDndBot.service.User;
import com.dnd.CharacterDndBot.service.acts.Act;
import com.dnd.CharacterDndBot.service.acts.ReturnAct;
import com.dnd.CharacterDndBot.service.acts.SingleAct;
import com.dnd.CharacterDndBot.service.acts.actions.Action;
import com.dnd.CharacterDndBot.service.acts.actions.BaseAction;
import com.dnd.CharacterDndBot.service.acts.actions.PoolActions;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.Refreshable.Time;
import com.dnd.CharacterDndBot.service.dndTable.dndService.Executor;
import com.dnd.CharacterDndBot.service.dndTable.dndService.Location;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RestMenu extends Executor<Action> {

	public RestMenu(Action action) {
		super(action);
	}

	@Override
	public Act executeFor(User user) {
		int condition = 0;
		if (action.getAnswers() != null) condition = action.getAnswers().length;
		switch (condition) {
		case 0:
			return startRest();
		case 1:
			return endRest(user);
		}
		log.error("RestMenu: out of bounds condition");
		return null;
	}

	private Act startRest() 
	{
		return ReturnAct.builder()
				.target(MENU_B)
				.act(SingleAct.builder()
						.name("startRest")
						.action(PoolActions.builder()
								.replyButtons()
								.actionsPool(new BaseAction[][] 
										{{Action.builder()
											.name("Long rest")
											.objectDnd(Time.LONG)
											.location(Location.REST)
											.build(),
											Action.builder()
											.name("Short rest")
											.objectDnd(Time.SHORT)
											.location(Location.REST)
											.build()},
									{Action.builder().name("RETURN TO MENU").build()}})
								.build())
						.text("You are resting... How many hours did you have time to rest?\n"
								+ "Long rest - if 8 or more.\n"
								+ "Short rest - if more than 1.5 and less than 8.")
						.build())
				.build();	
	}

	private Act endRest(User user) {
		Time time = (Time) action.getObjectDnd();
		user.getCharactersPool().getActual().refresh(time);
		if(time == Time.SHORT) {
			return SingleAct.builder()
					.name("EndRest")
					.text("Everything that depended on a short rest is reset.\n"
							+ "You have "+ user.getCharactersPool().getActual().getLvl().getLvl() +" Hit Dice available to restore your health.")
					.build();
		} else if(time.equals(Time.LONG)) { 
			return SingleAct.builder()
					.name("EndRest")
					.text("You are fully rested and recovered!")
					.build();
		} else {			
			return null;
		}
	}

}
