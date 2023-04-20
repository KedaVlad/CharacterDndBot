package app.bot.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import app.bot.event.ChatUpdate;
import app.bot.model.UserCore;

@Service
public class UserCacheService<T extends UserCore> {

	@Autowired
	private UserCoreService<T> userCoreService;
	private final Map<Long, UserCach<T>> cache = new ConcurrentHashMap<>();
	private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	private final int CACHE_TIMER = 60;

	public void put(T user) {
		Long userId = user.getId();
		UserCach<T> entry = new UserCach<>(user);
		cache.put(userId, entry);
		scheduleTimer(entry);
	}

	public boolean isCached(Long userId) {
		return cache.containsKey(userId);
	}

	public T get(Long userId) {
		UserCach<T> entry = cache.get(userId);
		if (entry != null) {	
			scheduleTimer(entry);
			return entry.getUser();
		} else {
			return null;
		}
	}

	private void scheduleTimer(UserCach<T> entry) {
		if (entry.getFuture() != null) {
			entry.getFuture().cancel(false);
		}

		entry.setFuture(executorService.schedule(() -> {
			T user = entry.getUser();
			cache.remove(user.getId(), entry);
			if (user != null) {
				eventPublisher.publishEvent(new ChatUpdate(user.clear()));
				userCoreService.save(user);
			}
		}, CACHE_TIMER, TimeUnit.MINUTES));
	}

	@PreDestroy
	private void closeExecutorService() {
		executorService.shutdown();
	}

	private static class UserCach<T extends UserCore> {
		private final T user;
		private ScheduledFuture<?> future;

		public UserCach(T user) {
			this.user = user;
		}

		public T getUser() {
			return user;
		}

		public ScheduledFuture<?> getFuture() {
			return future;
		}

		public void setFuture(ScheduledFuture<?> future) {
			this.future = future;
		}
	}
}
