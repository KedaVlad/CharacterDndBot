package app.dnd.service.gamer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.bot.model.act.Act;
import app.bot.model.act.ReturnAct;
import app.bot.model.act.SingleAct;
import app.bot.model.act.actions.Action;
import app.bot.model.act.actions.PoolActions;
import app.bot.model.user.User;
import app.dnd.service.Executor;
import app.dnd.service.Location;
import app.dnd.service.character.InformatorHandler;

@Component
public class Menu implements Executor<Action>{

	@Autowired
	private InformatorHandler informatorHandler;
	
	@Override
	public Act executeFor(Action action, User user) {

		user.getCharactersPool().save();
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
					.target(START_B)
					.act(SingleAct.builder()
							.name(MENU_B)
							.text(informatorHandler.handle(user.getCharactersPool().getActual()))
							.action(PoolActions.builder()
									.actionsPool(pool)
									.replyButtons()
									.build())
							.build())
					.build();
	}
}
