package com.dnd.CharacterDndBot.bot.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.dnd.CharacterDndBot.bot.model.act.ArrayActs;
import com.dnd.CharacterDndBot.bot.model.act.SingleAct;
import com.dnd.CharacterDndBot.bot.model.act.actions.Action;
import com.dnd.CharacterDndBot.bot.model.act.actions.BaseAction;
import com.dnd.CharacterDndBot.bot.model.user.Clouds;
import com.dnd.CharacterDndBot.bot.model.user.Script;
import com.dnd.CharacterDndBot.bot.model.user.User;
import com.dnd.CharacterDndBot.dnd.service.ButtonName;
import com.dnd.CharacterDndBot.dnd.service.Location;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UpdateHandler {
	@Autowired
	private CallbackHandler callbackHandler;
	@Autowired
	private MessageHandler messageHandler;

	public BaseAction handle(Update update, User user) {
		user.setTargetAct(null);
		if (update.hasCallbackQuery()) {
			return callbackHandler.handleFor(update.getCallbackQuery(), user);
		} else if (update.hasMessage()) {
			return messageHandler.handleFor(update.getMessage(), user);
		} else {
			log.error("HandlerFactory: Unattended type update");
			return null;
		}
	}
}

interface Handler<T> extends ButtonName {

	final static long CLOUD_ACT_K = 100000001;
	final static long MAIN_TREE_K = 100000002;

	public abstract BaseAction handleFor(T update, User user);
}

@Component
@Slf4j
class CallbackHandler implements Handler<CallbackQuery> {

	@Autowired
	private CallbackCloud callbackCloud;
	@Autowired
	private CallbackScript callbackScript;
	
	@Override
	public BaseAction handleFor(CallbackQuery callbackQuery, User user) {
		
		log.info("CallbackHandler call: " + callbackQuery.getData());
		
		String target = callbackQuery.getData().replaceAll("([a-zA-Z `�-]+)(\\d{9})(.+)", "$1");
		long key = Long.parseLong(callbackQuery.getData().replaceAll("([a-zA-Z `�-]+)(\\d{9})(.+)", "$2"));
		String callback = callbackQuery.getData().replaceAll("([a-zA-Z `�-]+)(\\d{9})(.+)", "$3");
		if (key == CLOUD_ACT_K) {
			return callbackCloud.handle(user.getCharactersPool().getClouds(), target, callback);
		} else {
			return callbackScript.handle(user.getScript(), target, callback, key);
		}
	}
}

@Slf4j
@Component
class CallbackCloud {

	public BaseAction handle(Clouds clouds, String target, String callback) {
		log.info("CallbackCloud target " + target + " callback " + callback);
		System.out.println(callback.equals(ButtonName.ELIMINATION_B));
		if (callback.equals(ButtonName.ELIMINATION_B)) {
			for (int i = 0; i < clouds.getCloudsWorked().size(); i++) {
				if (clouds.getCloudsWorked().get(i).getName().equals(target)) {
					clouds.getTrash().addAll(clouds.getCloudsWorked().get(i).getActCircle());
					clouds.getCloudsWorked().remove(i);
					return null;
				}
			}
			log.error("CallbackHandler: Eliminated act not found");
			return null;
		} else {
			for (SingleAct act : clouds.getCloudsWorked()) {
				if (act.getName().equals(target)) {
					return act.getAction().continueAction(callback);
				}
			}
			log.error("CallbackHandler: Cloud act not found");
			return null;
		}
	}
}

@Slf4j
@Component
class CallbackScript {

	public BaseAction handle(Script script, String target, String callback, long key) {
		log.info("CallbackScript: target: " + target + " callback: " + callback);
		if (script.targeting(target)) {
			if (key == Handler.MAIN_TREE_K) {
				return script.getMainTree().getLast().getAction().continueAction(callback);
			} else {
				ArrayActs pool = (ArrayActs) script.getMainTree().getLast();
				return pool.getTarget(key).getAction().continueAction(callback);
			}
		} else {
			log.error("CallbackScript: Act in main tree not found");
			return null;
		}
	}
}

@Component
class MessageHandler implements Handler<Message> {

	@Autowired
	private MessageComandEntity messageComandEntity;
	@Autowired
	private MessageScript messageScript;
	
	@Override
	public BaseAction handleFor(Message message, User user) {
		
		Script script = user.getScript();
		if(message.hasEntities()) {
			return messageComandEntity.handle(script, message);
		} else if(message.getText().equals(RETURN_TO_MENU) && user.getCharactersPool().hasReadyHero()) {
			user.getScript().getTrash().add(message.getMessageId());
			return Action.builder().location(Location.MENU).build();
		} else {
			return messageScript.handle(script, message);
		}	
	}
}

interface MessageSubHandler {
	
	public abstract BaseAction handle(Script script, Message message);
}

@Component
class MessageTextComand implements MessageSubHandler {

	@Override
	public BaseAction handle(Script script, Message message) {

		script.getTrash().add(message.getMessageId());
		Action action = Action.builder().location(Location.TEXT_COMAND).build();
		action.setAnswers(new String[] { message.getText() });
		return action;
	}
}

@Slf4j
@Component
class MessageComandEntity implements MessageSubHandler {

	@Autowired
	private MessageTextComand messageTextComand;

	@Override
	public BaseAction handle(Script script, Message message) {

		Optional<MessageEntity> commandEntity = message.getEntities().stream().filter(e -> "bot_command".equals(e.getType())).findFirst();

		if (commandEntity.isPresent()) {
			String comand = message.getText().substring(commandEntity.get().getOffset(),
					commandEntity.get().getLength());
			switch (comand) {
			case "/start":
				script.getTrash().add(message.getMessageId());
				return Action.builder().location(Location.START).build();

			case "/characters":
				script.getTrash().add(message.getMessageId());
				return Action.builder().location(Location.CHARACTER_CASE).build();
			}
		}
		log.error("MessageHandler: Comand not exist");
		return messageTextComand.handle(script, message);
	}
}


@Component
class MessageScript implements MessageSubHandler {

	@Autowired
	private MessageTextComand messageTextComand;

	public BaseAction handle(Script script, Message message) {

		if(findMediator(script, message) || findReply(script, message)) {

			if(script.getMainTree().getLast() instanceof ArrayActs) {
				ArrayActs pool = (ArrayActs) script.getMainTree().getLast();
				return pool.getPool()[0].getAction().continueAction(message.getText());
			} else {
				return script.getMainTree().getLast().getAction().continueAction(message.getText());
			}
		} else {
			return messageTextComand.handle(script, message);
		}
	}

	private boolean findReply(Script script, Message message) {
		for (int i = script.getMainTree().size() - 1; i > 0; i--) {
			if (script.getMainTree().get(i).hasReply(message.getText())) {
				script.backTo(i);
				script.getMainTree().getLast().getActCircle().add(message.getMessageId());
				return true;
			}
		}
		return false;
	}

	private boolean findMediator(Script script, Message message) {
	
		if (script.getMainTree().getLast().hasMediator()) {
			script.getMainTree().getLast().getActCircle().add(message.getMessageId());
			return true;
		} else if (script.getMainTree().size() > 1
				&& script.getMainTree().get(script.getMainTree().size() - 2).hasMediator()) {
			script.getTrash().addAll(script.getMainTree().getLast().getActCircle());
			script.getMainTree().removeLast();
			script.getMainTree().getLast().getActCircle().add(message.getMessageId());
			return true;
		} else {
			return false;
		}
	}

}