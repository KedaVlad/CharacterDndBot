package app.bot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import app.bot.model.act.Act;
import app.bot.model.act.ActiveAct;
import app.bot.model.act.ReturnAct;
import app.bot.model.act.SingleAct;
import app.bot.model.act.actions.BaseAction;
import app.bot.model.user.User;
import app.bot.model.wrapp.ActWrapp;
import app.bot.model.wrapp.BaseActionWrapp;
import app.dnd.service.ActionHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ActHandler implements EndHandler<ActWrapp, User> {

	private ReturnActInitializer returnActInitializer;
	@Autowired
	private ActiveActInitializer activeActInitializer;
	@Autowired
	private ActionHandler actionHandler;

	@Autowired
	public ActHandler() {
		returnActInitializer = new ReturnActInitializer();
	}

	@Override
	public User handle(ActWrapp wrapp) {
		Act act = wrapp.getTarget();

		if (act instanceof ReturnAct) {
			return returnActInitializer.initFor((ReturnAct) act, wrapp.getUser());
		} else if (act instanceof ActiveAct) {
			return activeActInitializer.initFor((ActiveAct) act, wrapp.getUser());
		} else {
			log.error("ActHandler: Unattended type act");
			return wrapp.getUser();
		}
	}

	private class ReturnActInitializer {

		public User initFor(ReturnAct act, User user) {

			if (act.getTarget() != null && user.getScript().targeting(act.getTarget(), user.getTrash())) {
				if (act.getAct() != null) {
					return activeActInitializer.initFor(act.getAct(), user);
				} else if (act.getAction() != null) {
					return handle(actionHandler.handle(new BaseActionWrapp(user, act.getAction())));
				} else if (act.getCall() != null && !act.getCall().equals(act.getTarget())) {
					BaseAction action = user.getScript().getMainTree().getLast().getAction()
							.continueAction(act.getCall());
					return handle(actionHandler.handle(new BaseActionWrapp(user, action)));
				} else {
					return user;
				}
			} else {
				log.error("ReturnActInitializer: Target is null or MainTree has no target from return act");
				return user;
			}
		}
	}

}

@Component
class ActiveActInitializer {

	public User initFor(ActiveAct act, User user) {
		if (act instanceof SingleAct && act.hasCloud()) {
			user.getClouds().getCloudsTarget().add((SingleAct) act);
			return user;
		} else {
			user.getScript().prepareScript(act.getName(), user.getTrash());
			user.setAct(act);
			return user;
		}
	}

}
