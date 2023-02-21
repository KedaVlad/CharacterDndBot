package com.dnd.CharacterDndBot.config;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.dnd.CharacterDndBot.dnd.dto.ObjectDnd;

public interface ObjectDndRepository extends MongoRepository<ObjectDnd, String> {
}