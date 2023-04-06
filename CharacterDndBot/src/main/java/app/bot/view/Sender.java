package app.bot.view;


import java.util.ArrayList;
import java.util.List;

import org.jvnet.hk2.annotations.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage.SendMessageBuilder;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import app.bot.model.ButtonModel;
import app.bot.model.MessageCore;
import app.bot.model.MessageModel;
import app.bot.model.UserCore;
import lombok.extern.slf4j.Slf4j;

public interface Sender {

	public void send(UserCore user, AbsSender absSender);
}

@Service
@Slf4j
class MessageSender implements Sender {

	@Override
	public void send(UserCore user,  AbsSender absSender) {
		sendMessage(user.toSend(), user.getId(), absSender);
		cleanTrash(user.toDelete(), user.getId(), absSender);
	}

	private void cleanTrash(List<Integer> trash, Long userId, AbsSender absSender) {
		for (Integer id : trash) {
			try {
				absSender.execute(DeleteMessage.builder().chatId(userId).messageId(id).build());
			} catch (TelegramApiException e) {
				log.error("MessageSendExecutor <cleanTrash> : " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	public void sendMessage(List<MessageCore> messageCore, Long userId, AbsSender absSender) {
		for(MessageCore core: messageCore) {
			for(MessageModel target: core.getMessage()) {
				try {
				Message message = absSender.execute(buildMessage(target, userId));
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
						.callbackData("" + name + buttonModel.getKey() + button)
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