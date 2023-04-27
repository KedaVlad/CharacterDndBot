package app.dnd.service.classes;

import java.util.List;

import app.dnd.model.ability.SaveRoll;
import app.dnd.model.commands.AddCommand;
import app.dnd.model.commands.CloudCommand;
import app.dnd.model.commands.InnerCommand;
import app.dnd.model.commands.UpCommand;
import app.dnd.model.enums.Proficiency;
import app.dnd.model.enums.Roll;
import app.dnd.model.enums.SaveRolls;
import app.dnd.model.enums.WeaponProperties;
import app.dnd.model.telents.attacks.AttackModification;
import app.dnd.model.telents.features.Feature;
import app.dnd.model.telents.features.InnerFeature;
import app.dnd.model.telents.proficiency.Possession;
import app.dnd.util.math.Dice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dnd.model.wrapp.ClassDndWrapp;
import app.dnd.repository.ClassDndWrappRepository;

import javax.annotation.PostConstruct;

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