package app.bot.conteiner;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.transaction.annotation.Transactional;

import app.bot.model.UserCore;


@Conteiner(name ="session")
@Transactional
public class SessionConteiner {
	
	private final Map<Long, UserCore> inSession = new ConcurrentHashMap<>();
	
	public void start(UserCore user) {
		inSession.put(user.getId(), user);
	}
	
	public void end(Long id) {
		inSession.remove(id);
	}
	
	public boolean isIn(Long id) {
		return inSession.containsKey(id);
	}
	
	
}
