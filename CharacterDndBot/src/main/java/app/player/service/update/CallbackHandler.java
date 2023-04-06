package app.player.service.update;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import app.dnd.model.actions.BaseAction;
import app.player.model.Key;
import app.player.model.act.ActiveAct;
import app.player.model.act.ArrayActs;
import app.player.model.enums.Button;
import app.user.model.Clouds;
import app.user.model.Trash;
import app.user.model.User;

@Component
class CallbackHandler {

	@Autowired
	private CallbackCloud callbackCloud;
	@Autowired
	private CallbackScript callbackScript;


	public void handle(User user) {

		CallbackQuery callbackQuery = user.getUpdate().getCallbackQuery();

		String target = callbackQuery.getData().replaceAll("([a-zA-Z `�-]+)(\\d{9})(.+)", "$1");
		long key = Long.parseLong(callbackQuery.getData().replaceAll("([a-zA-Z `�-]+)(\\d{9})(.+)", "$2"));
		String callback = callbackQuery.getData().replaceAll("([a-zA-Z `�-]+)(\\d{9})(.+)", "$3");

		if (key == Key.CLOUD.KEY) {
			user.setStage(callbackCloud.handle(user, target, callback));	
		} else {
			user.setStage(callbackScript.handle(user, target, callback, key)); 
		}
	}
}

@Component
class CallbackCloud {

	public BaseAction handle(User user, String target, String callback) {

		Clouds clouds = user.getClouds();
		ActiveAct act = clouds.findActInCloud(target);
		if (callback.equals(Button.ELIMINATION.NAME)) {
			Trash trash = user.getTrash();
			trash.getCircle().addAll(act.getActCircle());
			clouds.getCloudsWorked().remove(act);
			return null;
		} else {
			return act.getAction().continueAction(callback);
		}
	}
}

@Component
class CallbackScript {

	public BaseAction handle(User user, String target, String callback, long key) {

		ActiveAct act = user.getScript().getActByTargeting(target, user.getTrash());
		if (key == Key.TREE.KEY) {
			return act.getAction().continueAction(callback);
		} else {
			ArrayActs pool = (ArrayActs) act;
			return pool.getTarget(key).getAction().continueAction(callback);
		}
	}
}

