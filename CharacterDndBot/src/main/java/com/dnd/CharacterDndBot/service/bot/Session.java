package com.dnd.CharacterDndBot.service.bot;

import java.io.IOException;
import java.util.concurrent.BlockingQueue; 
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update; 
import com.dnd.CharacterDndBot.service.bot.user.ReadyToSend; 

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class Session { 

	@Autowired
	private Moderator moderator;
	private final BlockingQueue<Update> updateQueue = new LinkedBlockingQueue<>();
	final BlockingQueue<ReadyToSend> readyToWork = new LinkedBlockingQueue<>();
	private ExecutorService executor = Executors.newFixedThreadPool(10);
	private ExecutorService distributor = Executors.newSingleThreadExecutor(new ThreadFactory() {
		public Thread newThread(Runnable runnable) {
			Thread thread = Executors.defaultThreadFactory().newThread(runnable);
			thread.setDaemon(true);
			return thread;
		}
	});

	public void addUpdate(Update udate) {
		try {
			updateQueue.put(udate);
		} catch (InterruptedException e) {
			log.error("addUpdate: " + e.getMessage());
		}
	}

	public void run() {
		log.info("Session run");
		distributor.execute(() -> {
			while (true) {
				try {
					readyToWork.add(executor.submit(moderator.play(updateQueue.take())).get());
				} catch (InterruptedException | ExecutionException | ClassNotFoundException | IOException e) {
					log.error("Error in <Play> thread: " + e.getMessage());
				}
			}
		});
	}
}

