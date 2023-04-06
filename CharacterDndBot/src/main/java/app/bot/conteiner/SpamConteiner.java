package app.bot.conteiner;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.telegram.telegrambots.meta.api.objects.Update;

@Conteiner(name = "spam")
public class SpamConteiner {
	
	private final BlockingQueue<Update> spammQueue = new LinkedBlockingQueue<>();
	
	public void add(Update update) throws InterruptedException {
		spammQueue.put(update);
	}
	
	public Update get() throws InterruptedException {
		return spammQueue.take();
	}
}
