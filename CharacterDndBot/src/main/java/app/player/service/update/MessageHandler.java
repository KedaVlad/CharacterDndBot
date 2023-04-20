package app.player.service.update;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;

import app.dnd.model.actions.Action;
import app.player.model.Stage;
import app.player.model.act.ActiveAct;
import app.player.model.enums.Button;
import app.player.model.enums.Location;
import app.user.model.User;
import lombok.extern.slf4j.Slf4j;

@Component
public class MessageHandler {

	@Autowired
	private MessageComandEntity messageComandEntity;
	@Autowired
	private MessageScript messageScript;
 
	public Stage handle(Message message, User user) {

		if (message.hasEntities()) {

			user.getTrash().getCircle().add(message.getMessageId());
			return messageComandEntity.handle(message);
		} else if (message.getText().equals(Button.RETURN_TO_MENU.NAME)
				&& user.getActualHero().isReadyToGame()) {
			user.getTrash().getCircle().add(message.getMessageId());
			return Action.builder().location(Location.MENU).build();
		} else {
			return messageScript.handle(user, message);
		}
	}
}

@Slf4j
@Component
class MessageComandEntity {

	@Autowired
	private MessageTextComand messageTextComand;

	public Stage handle(Message message) {
		
		Optional<MessageEntity> commandEntity = message.getEntities().stream().filter(e -> "bot_command".equals(e.getType())).findFirst();

		if (commandEntity.isPresent()) {
			String comand = message.getText().substring(commandEntity.get().getOffset(),
					commandEntity.get().getLength());
			switch (comand) {
			case "/start":
				return Action.builder().location(Location.START).build();

			case "/characters":
				return Action.builder().location(Location.CHARACTER_CASE).build();
				
			case "/text_commands":
				return Action.builder().location(Location.TEXT_COMAND_HELPER).build();
				
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

	public Stage handle(User user, Message message) {

		ActiveAct act = user.getScript().getActByMassageTargeting(message, user.getTrash());

		if (act == null) {
			user.getTrash().getCircle().add(message.getMessageId());
			return messageTextComand.handle(message);
		} else {
			return act.continueAct(message.getText());
		}
	}
}

@Component
class MessageTextComand {

	public Stage handle(Message message) {
		Action action = Action.builder().location(Location.TEXT_COMAND).build();
		action.setAnswers(new String[] { message.getText() });
		return action;
	}
}
