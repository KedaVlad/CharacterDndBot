package com.dnd.CharacterDndBot.service;

import java.util.Optional;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.dnd.CharacterDndBot.service.acts.ArrayActs;
import com.dnd.CharacterDndBot.service.acts.SingleAct;
import com.dnd.CharacterDndBot.service.acts.actions.Action;
import com.dnd.CharacterDndBot.service.acts.actions.BaseAction;
import com.dnd.CharacterDndBot.service.dndTable.dndService.ButtonName;
import com.dnd.CharacterDndBot.service.dndTable.dndService.Location;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class HandlerFactory {
	
	public static Handler build(Update update) {
		if (update.hasCallbackQuery()) {
			return new CallbackHandler(update.getCallbackQuery());
		} else if (update.hasMessage()) {
			return new MessageHandler(update.getMessage());
		} else {
			log.error("HandlerFactory: Unattended type update");
			return new DefaultHandler();
		}
	}
}

interface Handler extends ButtonName {
	
	final static long CLOUD_ACT_K = 100000001;
	final static long MAIN_TREE_K = 100000002;

	public abstract BaseAction handleFor(User user);
}

class DefaultHandler implements Handler {
	
	@Override
	public BaseAction handleFor(User user) {
		return Action.builder().build();
	}
}

@Slf4j
class CallbackHandler implements Handler {
	private CallbackQuery callbackQuery;

	public CallbackHandler(CallbackQuery callbackQuery) {
		this.callbackQuery = callbackQuery;
	}

	@Override
	public BaseAction handleFor(User user) {
		String target = callbackQuery.getData().replaceAll("([a-zA-Z `�-]+)(\\d{9})(.+)", "$1");
		long key = Long.parseLong(callbackQuery.getData().replaceAll("([a-zA-Z `�-]+)(\\d{9})(.+)", "$2"));
		String callback = callbackQuery.getData().replaceAll("([a-zA-Z `�-]+)(\\d{9})(.+)", "$3");
		if (key == CLOUD_ACT_K) {
			return cloudWork(user.getCharactersPool().getClouds(), target, callback);
		} else {
			return scriptWork(user.getScript(), target, callback, key);
		}
	}

	private BaseAction cloudWork(Clouds clouds, String target, String callback) {
		if (callback.equals(ELIMINATION_B)) {
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

	private BaseAction scriptWork(Script script, String target, String callback, long key) {
		if (script.targeting(target)) {
			if (key == MAIN_TREE_K) {
				return script.getMainTree().getLast().getAction().continueAction(callback);
			} else {
				ArrayActs pool = (ArrayActs) script.getMainTree().getLast();
				return pool.getTarget(key).getAction().continueAction(callback);
			}
		} else {
			log.error("CallbackHandler: Act in main tree not found");
			return null;
		}
	}
}

@Slf4j
class MessageHandler implements Handler {
	
	private Message message;
	private Script script;

	public MessageHandler(Message message) {
		this.message = message;
	}

	@Override
	public BaseAction handleFor(User user) {
		this.script = user.getScript();
		if (message.hasEntities()) {
			return comandWork();
		} else if (message.getText().equals(RETURN_TO_MENU)) {
			user.getScript().getTrash().add(message.getMessageId());
			return Action.builder().location(Location.MENU).build();
		} else if (targetByText()) {
			if (script.getMainTree().getLast() instanceof ArrayActs) {
				ArrayActs pool = (ArrayActs) script.getMainTree().getLast();
				return pool.getPool()[0].getAction().continueAction(message.getText());
			} else {
				return script.getMainTree().getLast().getAction().continueAction(message.getText());
			}
		} else {
			script.getTrash().add(message.getMessageId());
			return characterTextComand();
		}
	}

	private BaseAction characterTextComand() {
		Action action = Action.builder().location(Location.TEXT_COMAND).build();
		action.setAnswers(new String[] { message.getText() });
		return action;
	}

	private BaseAction comandWork() {
		Optional<MessageEntity> commandEntity = message.getEntities().stream()
				.filter(e -> "bot_command".equals(e.getType())).findFirst();

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
		return characterTextComand();
	}

	private boolean targetByText() {
		return findMediator() || findReply(message.getText());
	}

	private boolean findReply(String string) {
		for (int i = script.getMainTree().size() - 1; i > 0; i--) {
			if (script.getMainTree().get(i).hasReply(string)) {
				script.backTo(i);
				script.getMainTree().getLast().getActCircle().add(message.getMessageId());
				return true;
			}
		}
		return false;
	}

	private boolean findMediator() {
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