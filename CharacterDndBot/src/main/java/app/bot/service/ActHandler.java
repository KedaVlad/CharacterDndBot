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
import app.dnd.service.ActionHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ActHandler {
	
	@Autowired
	private ActiveActInitializer activeActInitializer;
	@Autowired
	private ActionHandler actionHandler;
	private ReturnActInitializer returnActInitializer;
	
	@Autowired
	public ActHandler() {
		returnActInitializer = new ReturnActInitializer();
	}
	
	public User handle(Act act, User user) {
		
		if (act instanceof ReturnAct) {
			return returnActInitializer.initFor((ReturnAct)act, user);
		} else if (act instanceof ActiveAct) {
			return activeActInitializer.initFor((ActiveAct) act, user);
		} else {
			log.debug("ActHandler: Unattended type act");
			return user;
		}
	}
	
	private class ReturnActInitializer implements ActInitializer<ReturnAct> {
		
		@Override
		public User initFor(ReturnAct act, User user) {
			log.debug("ReturnActInitializer : target: "  + act.getTarget() + ", user: " + user.getId() + " , call: " + act.getCall());
			
			if (act.getTarget() != null && user.getScript().targeting(act.getTarget())) {
				if (act.getAct() != null) {
					return activeActInitializer.initFor(act.getAct(), user);
				} else if (act.getAction() != null) {
					return handle(actionHandler.handle(act.getAction(), user), user);
				} else if (act.getCall() != null && !act.getCall().equals(act.getTarget())) {
					BaseAction action = user.getScript().getMainTree().getLast().getAction().continueAction(act.getCall());
					return handle(actionHandler.handle(action, user), user);
				} else {
					return user;
				}
			} else {
				log.error("ReturnActInitializer: Target is null or MainTree has no target from return act");
				return null;
			}
		}
	}
	
}

interface ActInitializer<T extends Act> {
	public abstract User initFor(T t, User user);
}


@Component
class ActiveActInitializer implements ActInitializer<ActiveAct> {
	
	@Override
	public User initFor(ActiveAct act, User user) {
		if (act instanceof SingleAct && act.hasCloud()) {
			user.getCharactersPool().getClouds().getCloudsTarget().add((SingleAct) act);
			return user;
		} else {
			if (user.getScript().getMainTree().getLast().getName().equals(act.getName())) {
				user.getScript().getTrash().addAll(user.getScript().getMainTree().getLast().getActCircle());
				user.getScript().getMainTree().removeLast();
			}
			user.targetAct(act);
			return user;
		}
	}
}

