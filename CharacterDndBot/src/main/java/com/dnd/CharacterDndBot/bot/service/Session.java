package com.dnd.CharacterDndBot.bot.service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dnd.CharacterDndBot.bot.model.SessionConteiner;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Getter
public class Session { 

	@Autowired
	private Moderator moderator;
	@Autowired
	private SessionConteiner sessionConteiner;
	private ExecutorService executor = Executors.newFixedThreadPool(10);
	private ExecutorService distributor = Executors.newSingleThreadExecutor(new ThreadFactory() {
		public Thread newThread(Runnable runnable) {
			Thread thread = Executors.defaultThreadFactory().newThread(runnable);
			thread.setDaemon(true);
			return thread;
		}
	});

	public void run() {
		log.info("Session run");
		distributor.execute(() -> {
			while (true) {
				try {
					sessionConteiner.getReadyToWork().add(executor.submit(moderator.play(sessionConteiner.getUpdateQueue().take())).get());
				} catch (InterruptedException | ExecutionException | ClassNotFoundException | IOException e) {
					log.error("Error in <Session> thread: " + e.getMessage());
				}
			}
		});
	}
}

