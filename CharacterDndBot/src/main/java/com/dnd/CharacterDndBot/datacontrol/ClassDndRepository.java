package com.dnd.CharacterDndBot.datacontrol;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.dnd.CharacterDndBot.service.dndTable.dndDto.ClassDnd;

@Repository
public interface ClassDndRepository extends MongoRepository<ClassDnd, String> {
    
    List<ClassDnd> findByClassNameAndArchetype(String brand, String archetype);
    
    List<String> findDistinctClassName();
    
    List<String> findDistinctArchetypeByClassName(String className);
}