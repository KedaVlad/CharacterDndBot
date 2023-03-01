package app.bot.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;

import app.bot.model.act.ActiveAct;
import app.bot.model.act.ArrayActs;
import app.bot.model.act.SingleAct;
import app.bot.model.act.actions.Action;
import app.bot.model.act.actions.BaseAction;
import app.bot.model.user.Clouds;
import app.bot.model.user.Script;
import app.bot.model.user.Trash;
import app.bot.model.user.User;
import app.bot.model.wrapp.BaseActionWrapp;
import app.dnd.service.ButtonName;
import app.dnd.service.Location;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UpdateHandler implements StartHandler<User, BaseActionWrapp> {
	@Autowired
	private CallbackHandlerService callbackHandler;
	@Autowired
	private MessageHandler messageHandler;
	
	@Override
	public BaseActionWrapp handle(User user) {

		if (user.getUpdate().hasCallbackQuery()) {
			return callbackHandler.handle(user);
		} else if (user.getUpdate().hasMessage()) {
			return messageHandler.handle(user);
		} else {
			log.error("HandlerFactory: Unattended type update");
			return null;
		}
	}
}


@Component
class CallbackHandlerService implements StartHandler<User, BaseActionWrapp> {

	@Autowired
	private CallbackCloud callbackCloud;
	@Autowired
	private CallbackScript callbackScript;

	@Override
	public BaseActionWrapp handle(User user) {

		CallbackQuery callbackQuery = user.getUpdate().getCallbackQuery();
		String target = callbackQuery.getData().replaceAll("([a-zA-Z `�-]+)(\\d{9})(.+)", "$1");
		String callback = callbackQuery.getData().replaceAll("([a-zA-Z `�-]+)(\\d{9})(.+)", "$3");
		long key = Long.parseLong(callbackQuery.getData().replaceAll("([a-zA-Z `�-]+)(\\d{9})(.+)", "$2"));
		if (key == ButtonName.CLOUD_ACT_K) {
			return new BaseActionWrapp(user, callbackCloud.handle(user, target, callback));
		} else {
			return new BaseActionWrapp(user, callbackScript.handle(user, target, callback, key)); 
		}
	}
}

@Component
class CallbackCloud {

	public BaseAction handle(User user, String target, String callback) {

		Clouds clouds = user.getClouds();
		ActiveAct act = clouds.findActInCloud(target);
		if (callback.equals(ButtonName.ELIMINATION_B)) {
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
		if (key == ButtonName.MAIN_TREE_K) {
			return act.getAction().continueAction(callback);
		} else {
			ArrayActs pool = (ArrayActs) act;
			return pool.getTarget(key).getAction().continueAction(callback);
		}
	}
}

@Component
class MessageHandler implements StartHandler<User, BaseActionWrapp> {

	@Autowired
	private MessageComandEntity messageComandEntity;
	@Autowired
	private MessageScript messageScript;
	@Autowired
	private CharactersPoolControler charactersPoolControler;

	@Override
	public BaseActionWrapp handle(User user) {

		Message message = user.getUpdate().getMessage();
		if (message.hasEntities()) {
			user.getTrash().getCircle().add(message.getMessageId());
			return new BaseActionWrapp(user, messageComandEntity.handle(message));
		} else if (message.getText().equals(ButtonName.RETURN_TO_MENU)
				&& charactersPoolControler.hasReadyHeroById(message.getChatId())) {
			user.getTrash().getCircle().add(message.getMessageId());
			return new BaseActionWrapp(user, Action.builder().location(Location.MENU).build());
		} else {
			return new BaseActionWrapp(user, messageScript.handle(user, message));
		}
	}
}

interface MessageSubHandler {

	public abstract BaseAction handle(Script script, Message message);
}

@Component
class MessageTextComand {

	public BaseAction handle(Message message) {
		Action action = Action.builder().location(Location.TEXT_COMAND).build();
		action.setAnswers(new String[] { message.getText() });
		return action;
	}
}

@Slf4j
@Component
class MessageComandEntity {

	@Autowired
	private MessageTextComand messageTextComand;

	public BaseAction handle(Message message) {

		Optional<MessageEntity> commandEntity = message.getEntities().stream()
				.filter(e -> "bot_command".equals(e.getType())).findFirst();

		if (commandEntity.isPresent()) {
			String comand = message.getText().substring(commandEntity.get().getOffset(),
					commandEntity.get().getLength());
			switch (comand) {
			case "/start":
				return Action.builder().location(Location.START).build();

			case "/characters":
				return Action.builder().location(Location.CHARACTER_CASE).build();
			}
		}
		log.error("MessageHandler: Comand not exist");
		return messageTextComand.handle(message);
	}
}

@Component
class MessageScript {

	@Autowired
	private MessageTextComand messageTextComand;

	public BaseAction handle(User user, Message message) {

		ActiveAct act = user.getScript().getActByMassageTargeting(user.getUpdate().getMessage(), user.getTrash());

		if (act == null) {
			user.getTrash().getCircle().add(message.getMessageId());
			return messageTextComand.handle(message);
		} else if (act instanceof ArrayActs) {
			ArrayActs pool = (ArrayActs) act;
			return pool.getPool()[0].getAction().continueAction(message.getText());
		} else if (act instanceof SingleAct) {
			return act.getAction().continueAction(message.getText());
		} else {
			return null;
		}
	}
}