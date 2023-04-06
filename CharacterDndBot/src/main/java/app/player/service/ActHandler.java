package app.player.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.player.model.act.Act;
import app.player.model.act.ActiveAct;
import app.player.model.act.ReturnAct;
import app.player.model.act.SingleAct;
import app.player.service.stage.StageHandler;
import app.user.model.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EventHandler("actHandler")
public class ActHandler implements Handler<User> {

	private ReturnActInitializer returnActInitializer;
	@Autowired
	private ActiveActInitializer activeActInitializer;
	@Autowired
	private StageHandler actionHandler;

	@Autowired
	public ActHandler() {
		returnActInitializer = new ReturnActInitializer();
	}

	@Override
	public User handle(User userCore) {

		Act act = userCore.getAct();
		if (act instanceof ReturnAct) {
			returnActInitializer.initFor(userCore);
		} else if (act instanceof ActiveAct) {
			activeActInitializer.initFor(userCore);
		} else {
			log.error("ActHandler: Unattended type act");
		}
		return userCore;
	}

	private class ReturnActInitializer {

		public void initFor(User user) {

			ReturnAct act = (ReturnAct) user.getAct();

			if (act.getTarget() != null && user.getScript().targeting(act.getTarget(), user.getTrash())) {
				if (act.getAct() != null) {
					activeActInitializer.initFor(user);
				} else if (act.getAction() != null) {
					handle(actionHandler.handle(user));
				} else if (act.getCall() != null && !act.getCall().equals(act.getTarget())) {
					user.setStage(user.getScript().getMainTree().getLast().getAction().continueAction(act.getCall()));
					handle(actionHandler.handle(user));
				} else {
					log.error("ReturnActInitializer: Target is null or MainTree has no target from return act");
				}
			}
		}
	}
}

@Component
class ActiveActInitializer {

	public void initFor(User user) {
		Act act = user.getAct();
		if (act instanceof ActiveAct) {
			ActiveAct activeAct = (ActiveAct) act;
			if(activeAct.hasCloud()) {
				user.getClouds().getCloudsTarget().add((SingleAct) act);
				user.setMessage(null);
			} else {
				user.getScript().prepareScript(activeAct.getName(), user.getTrash());
				user.setMessage(activeAct);
			}
		}
	}

}
