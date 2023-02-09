package com.dnd.CharacterDndBot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage.SendMessageBuilder;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.dnd.CharacterDndBot.config.BotConfig;
import com.dnd.CharacterDndBot.service.acts.ActiveAct;
import com.dnd.CharacterDndBot.service.acts.ArrayActs;
import com.dnd.CharacterDndBot.service.acts.SingleAct;
import com.dnd.CharacterDndBot.service.acts.actions.BaseAction;

import lombok.extern.slf4j.Slf4j;

@Component
public class Bot extends TelegramLongPollingBot {
	
	final BotConfig config;
	final Session session;
	final MessageSender messageSender;

	public Bot(BotConfig config) {
		this.config = config;
		this.messageSender = new MessageSender(this);
		this.session = new Session(this);
	}

	@Override
	public void onUpdateReceived(Update update) {
		session.addUpdate(update);
	}

	@Override
	public String getBotUsername() {
		return config.getBotName();
	}

	@Override
	public String getBotToken() {
		return config.getToken();
	}

}

@Slf4j
class MessageSender {
	
	private final Bot bot;

	MessageSender(Bot bot) {
		this.bot = bot;
	}

	private void cleanTrash(ReadyToWork readyToSendActs) {
		for (Integer i : readyToSendActs.getTrash()) {
			try {
				bot.execute(DeleteMessage.builder().chatId(readyToSendActs.getId()).messageId(i).build());
			} catch (TelegramApiException e) {
				log.error("MessageSender (cleanTrash):" + e.getMessage());
			}
		}
	}

	public void sendMessage(ReadyToWork readyToSendActs) {
		try {
			for (ActiveAct act : readyToSendActs.getReadyToSend()) {
				if (act instanceof ArrayActs) {
					ArrayActs array = (ArrayActs) act;
					for (int i = 0; i < array.getPool().length; i++) {
						Message arrAct = bot
								.execute(buildMessage(array.getPool()[i], readyToSendActs.getId(), array.getKeys()[i]));
						act.toCircle(arrAct.getMessageId());
					}
				} else if (act instanceof SingleAct) {
					long key;
					if (act.hasAction() && act.getAction().isCloud()) {
						key = Handler.CLOUD_ACT_K;
					} else {
						key = Handler.MAIN_TREE_K;
					}
					Message message = bot.execute(buildMessage((SingleAct) act, readyToSendActs.getId(), key));
					act.toCircle(message.getMessageId());
				}
			}
		} catch (TelegramApiException e) {
			log.error("MessageSender (sendMessage):" + e.getMessage());
		}
		cleanTrash(readyToSendActs);
	}

	private SendMessage buildMessage(SingleAct act, long chatId, long key) {
		SendMessageBuilder builder = SendMessage.builder().text(act.getText()).chatId(chatId);
		if (act.getAction() != null && act.getAction().hasButtons()) {
			if (act.getAction().isReplyButtons()) {
				builder.replyMarkup(replyKeyboard(act.getAction()));
			} else {
				builder.replyMarkup(inlineKeyboard(act, key));
			}
		}
		return builder.build();
	}

	private InlineKeyboardMarkup inlineKeyboard(SingleAct act, long key) {
		List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
		String[][] target = act.getAction().buildButtons();
		for (String[] line : target) {
			List<InlineKeyboardButton> buttonLine = new ArrayList<>();
			for (String button : line) {
				buttonLine.add(
						InlineKeyboardButton.builder().text(button).callbackData(act.getName() + key + button).build());
			}
			buttons.add(buttonLine);
		}
		return InlineKeyboardMarkup.builder().keyboard(buttons).build();
	}

	private ReplyKeyboardMarkup replyKeyboard(BaseAction action) {
		List<KeyboardRow> buttons = new ArrayList<>();
		String[][] target = action.buildButtons();
		for (String[] line : target) {
			KeyboardRow keyboardRow = new KeyboardRow();
			List<KeyboardButton> row = new ArrayList<>();
			for (String button : line) {
				row.add(KeyboardButton.builder().text(button).build());
			}
			keyboardRow.addAll(row);
			buttons.add(keyboardRow);
		}
		return ReplyKeyboardMarkup.builder().keyboard(buttons).resizeKeyboard(true).build();
	}
}
