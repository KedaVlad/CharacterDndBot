package app.bot.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.springframework.beans.factory.annotation.Autowired;
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

import app.bot.model.Bot;
import app.bot.model.act.ActiveAct;
import app.bot.model.act.ArrayActs;
import app.bot.model.act.SingleAct;
import app.bot.model.act.actions.BaseAction;
import app.bot.model.user.ReadyToSend;
import app.bot.model.user.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MessageSender {

	@Autowired
	private UserFileService userFileService;
	@Autowired
	private Bot bot;

	private ExecutorService distributor  = Executors.newSingleThreadExecutor(new ThreadFactory() {
		public Thread newThread(Runnable runnable) {
			Thread thread = Executors.defaultThreadFactory().newThread(runnable);
			thread.setDaemon(true);
			return thread;
		}
	}); 

	public void run() {
		log.info("MessageSender run");
		distributor.execute(() -> {
			while (true) {
				try {
					sendMessage(bot.getUpdateManager().getSessionConteiner().getReadyToWork().take());
				} catch (InterruptedException e) {
					log.error("Error in <MessageSender> thread: " + e.getMessage());
				}
			}
		});
	}

	private void cleanTrash(ReadyToSend readyToSendActs) {
		try {
			for (Integer i : readyToSendActs.getTrash()) {
				bot.execute(DeleteMessage.builder().chatId(readyToSendActs.getId()).messageId(i).build());
			}
		} catch (TelegramApiException e) {
			log.error("MessageSender (cleanTrash):" + e.getMessage());
		}
	}

	public void sendMessage(User user) {
		
		ReadyToSend readyToSendActs = user.makeSend();
		log.info("MessageSender sendMessage " + readyToSendActs.getReadyToSend().size() + "  " + readyToSendActs.getTrash().size());
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
			log.error("MessageSender (send Message):" + e.getMessage());
		}
		cleanTrash(readyToSendActs);
		try {
			userFileService.save(user);
		} catch (IOException e) {
			log.error("MessageSender (save user):" + e.getMessage());
		}
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
						InlineKeyboardButton.builder().text(button).callbackData(""+act.getName() + key + button).build());
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
