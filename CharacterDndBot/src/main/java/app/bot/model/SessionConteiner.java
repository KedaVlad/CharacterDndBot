package app.bot.model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import app.bot.model.user.User;
import lombok.Data;
@Data
@Component
public class SessionConteiner {

	private final BlockingQueue<Update> updateQueue = new LinkedBlockingQueue<>();
	private final BlockingQueue<User> readyToWork = new LinkedBlockingQueue<>();
}
