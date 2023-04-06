package app.dnd.service.logic.talants.feature;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.dnd.model.ability.Ability;
import app.dnd.repository.AbilityRepository;

@Transactional
@Service
public class AbilityService {

	@Autowired
	private AbilityRepository abilityRepository;

	public Ability getById(Long id) {
		Optional< Ability> userOptional = abilityRepository.findById(id);
		if (userOptional.isPresent()) {
			return userOptional.get();
		} else {
			return Ability.build(id);
		}
	}

	public void save(Ability attackAbility) {
		abilityRepository.save(attackAbility);
	}

}
