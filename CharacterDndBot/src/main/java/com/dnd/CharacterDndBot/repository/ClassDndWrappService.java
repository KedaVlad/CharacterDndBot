package com.dnd.CharacterDndBot.repository;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dnd.CharacterDndBot.dnd.dto.wrap.ClassDndWrapp;

@Service
public class ClassDndWrappService {

	@Autowired
	private ClassDndWrappRepository classDndWrappRepository;

	public List<String> findDistinctClassName() {
		return classDndWrappRepository.findDistinctClassName();
	}

	public List<String> findDistinctArchetypeByClassName(String className) {
		return classDndWrappRepository.findDistinctArchetypeByClassName(className);
	}

	public String findDistinctInformationByClassNameAndArchetype(String className, String archetype) {
		return classDndWrappRepository.findDistinctInformationByClassNameAndArchetype(className, archetype);
	}

	public ClassDndWrapp findByClassNameAndArchetype(String className, String archetype) {
		return classDndWrappRepository.findByClassNameAndArchetype(className, archetype);
	}

}