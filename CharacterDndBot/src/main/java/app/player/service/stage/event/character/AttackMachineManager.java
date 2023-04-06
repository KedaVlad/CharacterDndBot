package app.player.service.stage.event.character;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.ability.attacks.AttackModification;
import app.dnd.model.actions.Action;
import app.dnd.model.actions.PreRoll;
import app.dnd.model.stuffs.items.Weapon;
import app.dnd.service.logic.DndFacade;
import app.player.model.act.SingleAct;
import app.player.model.enums.Location;
import app.player.service.stage.EventExecutor;
import app.player.service.stage.event.UserExecutor;
import app.user.model.User;

@EventExecutor(Location.ATTACK_MACHINE)
public class AttackMachineManager implements UserExecutor {

	@Autowired
	private AttackMachineExecutor attackMachineExecutor;

	@Override
	public void executeFor(User user) {

		if(user.getStage() instanceof Action) {	
			Action action = (Action) user.getStage();

			if (action.getObjectDnd() == null) {
				attackMachineExecutor.postHit(user);

			} else if (action.getObjectDnd() instanceof Weapon) {
				attackMachineExecutor.preAttack(user);

			} else if (action.getObjectDnd() instanceof AttackModification) {
				attackMachineExecutor.postAttack(user);

			}
		} else if(user.getStage() instanceof PreRoll) {
			attackMachineExecutor.preHit(user);
		}
	}
}

@Component
class AttackMachineExecutor {

	@Autowired
	private DndFacade dndFacade;

	void preAttack(User user) {
		user.setAct(SingleAct.builder()
				.name("StartAttack")
				.action(dndFacade.action().attack().preAttack(user.getStage(), user.getId()))
				.build());
	}

	void postAttack(User user) {
		user.setAct(SingleAct.builder()
				.name("MakeAttack")
				.action(dndFacade.action().attack().postAttack(user.getStage(), user.getId()))					
				.build());
	}

	void preHit(User user) {
		user.setAct(SingleAct.builder()
				.name("preHit")
				.action(dndFacade.action().attack().preHit(user.getStage(), user.getId()))
				.build());
	}
	
	void postHit(User user) {
		user.setAct(SingleAct.builder()
				.name("postHit")
				.action(dndFacade.action().attack().postHit(user.getStage(), user.getId()))
				.build());
	}

}
