package com.dnd.CharacterDndBot.bot.model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import lombok.Getter;

@Getter
@Component
public class SpamConteiner {
	private final BlockingQueue<Update> spammQueue = new LinkedBlockingQueue<>();
}
