package app.player.service.stage.event.hero;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.actions.Action;
import app.dnd.model.actions.PreRoll;
import app.dnd.model.actions.RollAction;
import app.dnd.model.stuffs.items.Weapon;
import app.dnd.model.telents.attacks.AttackModification;
import app.dnd.service.DndFacade;
import app.player.event.UserEvent;
import app.player.model.EventExecutor;
import app.player.model.Stage;
import app.player.model.act.Act;
import app.player.model.act.ReturnAct;
import app.player.model.act.SingleAct;
import app.player.model.enums.Button;
import app.player.model.enums.Location;
import app.player.service.stage.Executor;
import app.user.model.ActualHero;

@EventExecutor(Location.ATTACK_MACHINE)
public class AttackMachineManager implements Executor {

	@Autowired
	private AttackMachineExecutor attackMachineExecutor;

	@Override
	public Act execute(UserEvent<Stage> event) {

		if(event.getTask() instanceof Action) {	
			Action action = (Action) event.getTask();

			if (action.getObjectDnd() instanceof Weapon) {
				return attackMachineExecutor.preAttack(event.getUser().getActualHero(), event.getTask());

			} else if (action.getObjectDnd() instanceof AttackModification) {
				return attackMachineExecutor.postAttack(event.getUser().getActualHero(), event.getTask());

			} else {
				return SingleAct.builder().name("Miss").stage(Action.builder().text("GOODLUCK NEXT TIME").build()).build();
			}
		} else if(event.getTask() instanceof PreRoll) {
			return attackMachineExecutor.preHit(event.getUser().getActualHero(), event.getTask());
		} else if (event.getTask() instanceof RollAction) {
			return attackMachineExecutor.postHit(event.getUser().getActualHero(), event.getTask());
		} else {
			return ReturnAct.builder().target(Button.MENU.NAME).build();
		}
	}

}

@Component
class AttackMachineExecutor {

	@Autowired
	private DndFacade dndFacade;

	Act preAttack(ActualHero hero, Stage stage) {
		return SingleAct.builder()
				.name("StartAttack")
				.stage(dndFacade.action().attack().preAttack(hero, stage))
				.build();
	}

	Act postAttack(ActualHero hero, Stage stage) {
		return SingleAct.builder()
				.name("MakeAttack")
				.stage(dndFacade.action().attack().postAttack(hero, stage))				
				.build();
	}

	Act preHit(ActualHero hero, Stage stage) {
		return SingleAct.builder()
				.name("preHit")
				.stage(dndFacade.action().attack().preHit(hero, stage))
				.build();
	}
	
	Act postHit(ActualHero hero, Stage stage) {
		return SingleAct.builder()
				.name("postHit")
				.stage(dndFacade.action().attack().postHit(hero, stage))
				.build();
	}

}
