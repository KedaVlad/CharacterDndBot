package app.player.service.stage.event.character;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.actions.Action;
import app.dnd.model.actions.PoolActions;
import app.dnd.service.logic.stuff.AcInformator;
import app.player.model.act.Act;
import app.player.model.act.ReturnAct;
import app.player.model.act.SingleAct;
import app.player.model.enums.Location;
import app.player.service.stage.Executor;
import app.user.model.User;

@Component
public class Menu implements Executor<Action>{

	@Autowired
	private AcInformator acInformator;

	@Override
	public Act executeFor(Action action, User user) {

		Action[][] pool = new Action[][] {
			{ 
				Action.builder().name(ABILITY_B).location(Location.TALANT).build(),
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
							.text(user.getActualHero().getCharacter().getInformation() 
									+ "\n" + acInformator.getInformation(user.getActualHero().getCharacter()))
							.action(PoolActions.builder()
									.actionsPool(pool)
									.replyButtons()
									.build())
							.build())
					.build();
	}
}
