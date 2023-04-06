package app.player.service.stage;

import org.springframework.beans.factory.annotation.Autowired;

import app.player.service.EventHandler;
import app.player.service.Handler;
import app.user.model.User;

@EventHandler("stageHandler")
public class StageHandler implements Handler<User> {

	@Autowired
	private StageManager<User> stageManager;

	@Override
	public User handle(User userCore) {
		if(userCore.getStage() != null) {
			stageManager.find(userCore.getStage().getLocation()).executeFor(userCore);
		}
		return userCore;
	}
}
