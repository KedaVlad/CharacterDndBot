package app.bot.conteiner;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import app.bot.model.UserCore;

@Conteiner(name = "user")
public class UserCoreConteiner<T extends UserCore> {
	
	private final BlockingQueue<T> updateQueue = new LinkedBlockingQueue<>();

}
