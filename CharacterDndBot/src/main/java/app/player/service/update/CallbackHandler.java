package app.player.service.update;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import app.player.model.Key;
import app.player.model.Stage;
import app.player.model.act.CloudAct;
import app.player.model.enums.Button;
import app.bot.model.user.User;

@Component
public class CallbackHandler {

	public Stage handle(CallbackQuery callbackQuery, User user) {

		String target = callbackQuery.getData().replaceAll("([a-zA-Z `�-]+)(\\d{5})(.+)", "$1");
		Integer key = Integer.parseInt(callbackQuery.getData().replaceAll("([a-zA-Z `�-]+)(\\d{5})(.+)", "$2"));
		String callback = callbackQuery.getData().replaceAll("([a-zA-Z `�-]+)(\\d{5})(.+)", "$3");

		if (key == Key.CLOUD.KEY) {
			CloudAct act = user.getActualHero().findActInCloud(target);
			if (callback.equals(Button.ELIMINATION.NAME)) {
				user.getTrash().getCircle().addAll(act.getActCircle());
				user.getActualHero().getCloudsWorked().remove(act);
				return null;
			} else {
				return act.continueAct(callback);
			}
		} else if (key == Key.TREE.KEY) {
			return user.getScript().getActByTargeting(target, user.getTrash()).continueAct(callback);
		} else {
			return user.getScript().getActByTargeting(target, user.getTrash()).continueAct(key + callback);
		}
	}
}


