package app.player.service.update;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;

import app.dnd.model.actions.Action;
import app.dnd.model.actions.BaseAction;
import app.player.model.act.ActiveAct;
import app.player.model.act.ArrayActs;
import app.player.model.act.SingleAct;
import app.player.model.enums.Button;
import app.player.model.enums.Location;
import app.user.model.User;
import lombok.extern.slf4j.Slf4j;

@Component
class MessageHandler {

	@Autowired
	private MessageComandEntity messageComandEntity;
	@Autowired
	private MessageScript messageScript;
 
	public void handle(User user) {

		Message message = user.getUpdate().getMessage();
		if (message.hasEntities()) {
			user.getTrash().getCircle().add(message.getMessageId());
			user.setStage(messageComandEntity.handle(message));
		} else if (message.getText().equals(Button.RETURN_TO_MENU.NAME)
				&& user.getActualHero().hasReadyHero()) {
			user.getTrash().getCircle().add(message.getMessageId());
			user.setStage( Action.builder().location(Location.MENU).build());
		} else {
			user.setStage(messageScript.handle(user, message));
		}
	}
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