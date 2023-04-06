package app.dnd.service.logic.attack;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.dnd.model.ability.attacks.AttackAbility;
import app.dnd.repository.AttackAbilityRepository;

@Transactional
@Service
public class AttackAbilityService {

	@Autowired
	private AttackAbilityRepository attackAbilityRepository;

	public AttackAbility getById(Long id) {
		Optional<AttackAbility> userOptional = attackAbilityRepository.findById(id);
		if (userOptional.isPresent()) {
			return userOptional.get();
		} else {
			return AttackAbility.build(id);
		}
	}

	public void save(AttackAbility attackAbility) {
		attackAbilityRepository.save(attackAbility);
	}

}

