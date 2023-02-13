package com.dnd.CharacterDndBot.service.dndTable.dndService.userService;

import org.springframework.stereotype.Component;

import com.dnd.CharacterDndBot.service.acts.Act;
import com.dnd.CharacterDndBot.service.acts.ReturnAct;
import com.dnd.CharacterDndBot.service.acts.SingleAct;
import com.dnd.CharacterDndBot.service.acts.actions.Action;
import com.dnd.CharacterDndBot.service.acts.actions.PoolActions;
import com.dnd.CharacterDndBot.service.bot.user.User;
import com.dnd.CharacterDndBot.service.dndTable.dndService.Executor;
import com.dnd.CharacterDndBot.service.dndTable.dndService.Location;
import com.dnd.CharacterDndBot.service.dndTable.dndService.characterService.InformatorFactory;

@Component
public class Menu implements Executor<Action>{

	@Override
	public Act executeFor(Action action, User user) {

		Action[][] pool = new Action[][] {
			{ 
				Action.builder().name(ABILITY_B).location(Location.ABILITY).build(),
				Action.builder().name(CHARACTERISTIC_B).location(Location.CHARACTERISTIC).build(),
				Action.builder().name(STUFF_B).location(Location.STUFF).build()
			},
			{ 
				Action.builder().name(ROLLS_B).location(Location.ROLLS).build(),
				Action.builder().name(DEBUFF_B).location(Location.DEBUFF).build()
			},
			{ 
				Action.builder().name(REST_B).location(Location.REST).build(),
				Action.builder().name(MEMOIRS_B).location(Location.MEMOIRS).build() 
			}};

			return ReturnAct.builder()
					.target(ABILITY_B)
					.act(SingleAct.builder()
							.name(MENU_B)
							.text(InformatorFactory.build(user.getCharactersPool().getActual()).getInformation())
							.action(PoolActions.builder()
									.actionsPool(pool)
									.replyButtons()
									.build())
							.build())
					.build();
	}
}
