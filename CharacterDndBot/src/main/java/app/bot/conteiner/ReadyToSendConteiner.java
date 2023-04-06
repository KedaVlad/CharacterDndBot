package app.bot.conteiner;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.transaction.annotation.Transactional;

import app.bot.model.UserCore;

@Conteiner(name = "readyToSend")
@Transactional
public class ReadyToSendConteiner<T extends UserCore> {
	
	private final BlockingQueue<T> readyToWork = new LinkedBlockingQueue<>();

	public T get() throws InterruptedException {
		return readyToWork.take();
	}

	public void add(T user) throws InterruptedException {
		readyToWork.put(user);
	}
	
}
