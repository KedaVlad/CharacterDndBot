package app.player.service.update;

import org.springframework.beans.factory.annotation.Autowired;

import app.player.service.EventHandler;
import app.player.service.Handler;
import app.user.model.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EventHandler("updateHandler")
public class UpdateHandler implements Handler<User> {
	
	@Autowired
	private CallbackHandler callbackHandler;
	@Autowired
	private MessageHandler messageHandler;
	

	@Override
	public User handle(User userCore) {
		if (userCore.getUpdate().hasCallbackQuery()) {
			callbackHandler.handle(userCore);
		} else if (userCore.getUpdate().hasMessage()) {
			messageHandler.handle(userCore);
		} else {
			log.error("HandlerFactory: Unattended type update");
		}
		return userCore;
	}
}





