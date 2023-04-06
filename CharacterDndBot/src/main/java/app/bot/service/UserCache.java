package app.bot.service;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.bot.model.UserCore;
import app.bot.view.View;
import app.user.model.User;

@Component
public class UserCache<T extends UserCore> {

	
	@Autowired
	private UserCoreService<T> userService;
	@Autowired
	private ConcurrentHashMap<Long, User> cache = new ConcurrentHashMap<>();
 
   
    public UserCache(UserService userService) {
        this.userService = userService;
    }
    
    public void put(User user) {
        long userId = user.getId();
        cache.put(userId, user);
        scheduleTimer(userId);
    }
    
    public boolean checkByKey(long userId) {
        return cache.containsKey(userId);
    }
    
    public User get(long userId) {
        User user = cache.get(userId);
        if (user != null) {
            scheduleTimer(userId); // reset timer for the user
        }
        return user;
    }
    
    private void scheduleTimer(long userId) {
        executorService.schedule(() -> {
            User user = cache.remove(userId);
            if (user != null) {
                userService.save(user); // auto-save user to the database
            }
        }, 1, TimeUnit.HOURS);
    }
}
