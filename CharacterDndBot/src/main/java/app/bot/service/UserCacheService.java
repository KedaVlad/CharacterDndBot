package app.bot.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;

import app.bot.model.user.User;
import lombok.Data;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import app.bot.event.ChatUpdate;

@Service
public class UserCacheService {

	private final UserService userService;
	private final Map<Long, ActiveUserCache> cache = new ConcurrentHashMap<>();
	private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
	private final ApplicationEventPublisher eventPublisher;
	private final static int CACHE_TIMER = 60;

	public UserCacheService(UserService userService, ApplicationEventPublisher eventPublisher) {
		this.userService = userService;
		this.eventPublisher = eventPublisher;
	}

	public User getById(Long id) {
		if(cache.containsKey(id)) {
			ActiveUserCache entry = cache.get(id);
				scheduleTimer(entry);
				return entry.getUser();
		} else {
			User user = userService.getById(id);
			put(user);
			return user;
		}
	}

	private void put(User user) {
		ActiveUserCache entry = new ActiveUserCache(user);
		cache.put(user.getId(), entry);
		scheduleTimer(entry);
	}

	private void scheduleTimer(ActiveUserCache entry) {
		if (entry.getFuture() != null) {
			entry.getFuture().cancel(false);
		}
		entry.setFuture(executorService.schedule(() -> {
			User user = entry.getUser();
			eventPublisher.publishEvent(new ChatUpdate(this, user.clear()));
			userService.save(user);
			cache.remove(user.getId(), entry);
		}, CACHE_TIMER, TimeUnit.MINUTES));
	}

	@PreDestroy
	private void closeExecutorService() {
		for(Long id: cache.keySet()) {
			userService.save(cache.get(id).getUser());
		}
		executorService.shutdown();
	}

	@Data
	private static class ActiveUserCache {
		private final User user;
		private ScheduledFuture<?> future;

		public ActiveUserCache(User user) {
			this.user = user;
		}

	}
}
