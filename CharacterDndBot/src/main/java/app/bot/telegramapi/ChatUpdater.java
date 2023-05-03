package app.bot.telegramapi;


import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage.SendMessageBuilder;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import app.bot.model.event.ChatUpdate;
import app.bot.model.event.EndSession;
import app.bot.model.button.ButtonModel;
import app.bot.model.message.MessageCore;
import app.bot.model.message.MessageModel;
import app.bot.model.user.UserCore;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ChatUpdater {

	private final Bot bot;
	private final ApplicationEventPublisher applicationEventPublisher;

	public ChatUpdater(Bot bot, ApplicationEventPublisher applicationEventPublisher) {
		this.bot = bot;
		this.applicationEventPublisher = applicationEventPublisher;
	}
	
	@EventListener
	public void complete(ChatUpdate chatUpdate) {
		UserCore user = chatUpdate.getUser();
		sendMessage(user.toSend(), user.getId());
		cleanTrash(user.toDelete(), user.getId());
		applicationEventPublisher.publishEvent(new EndSession(this, user.getId()));
	}

	 void cleanTrash(List<Integer> trash, Long userId) {
		for (Integer id : trash) {
			try {
				bot.execute(DeleteMessage.builder().chatId(userId).messageId(id).build());
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
		}
	}

	private void sendMessage(List<MessageCore> messageCore, Long userId) {
		for(MessageCore core: messageCore) {
			for(MessageModel target: core.getMessage()) {
				try {
				Message message = bot.execute(buildMessage(target, userId));
					core.catchId(message.getMessageId());
				} catch (TelegramApiException e) {
					log.error("MessageSendExecutor <sendMessage> : " + e.getMessage());
				}
			}
		}
	}

	private SendMessage buildMessage(MessageModel message, Long id) {
		SendMessageBuilder builder = SendMessage.builder().text(message.getText()).chatId(id);
		if (message.hasButton()) {
			if (message.getButtonModel().isReply()) {
				builder.replyMarkup(replyKeyboard(message.getButtonModel()));
			} else {
				builder.replyMarkup(inlineKeyboard(message.getButtonModel(), message.getName()));
			}
		}
		return builder.build();
	}

	private InlineKeyboardMarkup inlineKeyboard(ButtonModel buttonModel, String name) {
		List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
		String[][] target = buttonModel.getButton();
		for (String[] line : target) {
			List<InlineKeyboardButton> buttonLine = new ArrayList<>();
			for (String button : line) {
				buttonLine.add(InlineKeyboardButton.builder()
						.text(button)
						.callbackData(name + buttonModel.getKey() + button)
						.build());
			}
			buttons.add(buttonLine);
		}
		return InlineKeyboardMarkup.builder().keyboard(buttons).build();
	}

	private ReplyKeyboardMarkup replyKeyboard(ButtonModel buttonModel) {
		
		List<KeyboardRow> buttons = new ArrayList<>();
		String[][] target = buttonModel.getButton();
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