package com.dnd.CharacterDndBot.bot.service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class IdManager {

	private final List<Long> inSession = new CopyOnWriteArrayList<>();
}
