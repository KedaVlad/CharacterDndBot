package app.player.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import app.bot.service.Player;
import app.user.model.User;

@Service
public class GamePlayer implements Player<User> {

	private List<Handler<User>> handlers;
	
	@Autowired
	public GamePlayer(@Qualifier("handlers") List<Handler<User>> handlers) {
       this.handlers = handlers;
    }
	
	@Override
	public User playFor(User user) {

		for(Handler<User> handler: handlers) {
			handler.handle(user);
		}
		return user;
	}

}
