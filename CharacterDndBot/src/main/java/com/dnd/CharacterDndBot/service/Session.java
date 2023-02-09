package com.dnd.CharacterDndBot.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;

import org.telegram.telegrambots.meta.api.objects.Update;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Session { 

	private volatile Moderator moderator;
	private final Bot bot;
	private final BlockingQueue<Update> updateQueue;
	private final BlockingQueue<ReadyToWork> readyToWork;
	private ExecutorService executor;
	private ExecutorService distributor;

	Session(Bot bot) {
		this.bot = bot;
		this.moderator = new Moderator();
		updateQueue = new LinkedBlockingQueue<>();
		readyToWork = new LinkedBlockingQueue<>();
		executor = Executors.newFixedThreadPool(10);
		distributor = Executors.newFixedThreadPool(2, new ThreadFactory() {
			public Thread newThread(Runnable runnable) {
				Thread thread = Executors.defaultThreadFactory().newThread(runnable);
				thread.setDaemon(true);
				return thread;
			}
		});
		run();
	}

	public void addUpdate(Update udate) {
		try {
			updateQueue.put(udate);
		} catch (InterruptedException e) {
			log.error(e.getMessage());
		}
	}

	private void run() {
		distributor.execute(() -> {
			while (true) {
				try {
					bot.messageSender.sendMessage(readyToWork.take());
				} catch (InterruptedException e) {
					log.error("Error occured: " + e.getMessage());
				}
			}
		});

		distributor.execute(() -> {
			while (true) {
				try {
					readyToWork.add(executor.submit(moderator.play(updateQueue.take())).get());
				} catch (InterruptedException | ExecutionException e) {
					log.error("Error occured: " + e.getMessage());
				}
			}
		});
	}
}

class Moderator {

	private Map<Long, User> users;
	{
		users = new HashMap<>();
	}

	private User initUser(long id) {
		if (users.containsKey(id)) {
			return users.get(id);
		} else {
			users.put(id, new User(id));
			return users.get(id);
		}
	}

	private long beacon(Update update) {
		if (update.hasCallbackQuery()) {
			return update.getCallbackQuery().getMessage().getChatId();
		} else {
			return update.getMessage().getChatId();
		}
	}

	GameTable play(Update update) {
		return new GameTable(initUser(beacon(update)), update);
	}
}
