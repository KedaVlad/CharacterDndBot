package app.player.service.stage.event.character;

import org.springframework.beans.factory.annotation.Autowired;

import app.dnd.service.logic.DndFacade;
import app.player.model.act.ArrayActs;
import app.player.model.act.ReturnAct;
import app.player.model.act.SingleAct;
import app.player.model.enums.Button;
import app.player.model.enums.Location;
import app.player.service.stage.EventExecutor;
import app.player.service.stage.event.UserExecutor;
import app.user.model.User;

@EventExecutor(Location.CHARACTER_CASE)
public class CharacterCaseExecutor implements UserExecutor {

	@Autowired
	private DndFacade dndFacade;

	@Override
	public void executeFor(User user) {

		if(dndFacade.hero().characterPool().isEmpty(user.getId())) {
			user.setAct(SingleAct.builder()
					.name(Button.CHARACTER_CASE.NAME)
					.text("You don't have a Hero yet, my friend. But after you " + Button.CREATE.NAME
							+ " them, you can find them here.")
					.action(dndFacade.action().characterPool().create())
					.build());

		} else {
			user.setAct(ReturnAct.builder()
					.target(Button.START.NAME)
					.act(ArrayActs.builder()
							.name(Button.CHARACTER_CASE.NAME)
							.pool(SingleAct.builder()
									.text("Choose the Hero or " + Button.CREATE.NAME + " new one.")
									.action(dndFacade.action().characterPool().create())
									.build(),
									SingleAct.builder()
									.text("Your Heroes")
									.action(dndFacade.action().characterPool().download(user.getId()))
									.build())
							.build())
					.build());

		}
	}
}
