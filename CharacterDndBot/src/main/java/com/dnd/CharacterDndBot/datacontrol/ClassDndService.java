package com.dnd.CharacterDndBot.datacontrol;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dnd.CharacterDndBot.service.dndTable.dndDto.ClassDnd;

@Service
public class ClassDndService {

	@Autowired
    private ClassDndRepository classDndRepository;

	public List<String> findDistinctClassName() {
		return classDndRepository.findDistinctClassName();
	}
  
	public List<String> findDistinctArchetypeByClassName(String className) {
		return classDndRepository.findDistinctArchetypeByClassName(className);
	}
    
	public List<ClassDnd>  findByClassNameAndArchetype(String className, String archetype) {
		return classDndRepository.findByClassNameAndArchetype(className, archetype);
	}
    
}