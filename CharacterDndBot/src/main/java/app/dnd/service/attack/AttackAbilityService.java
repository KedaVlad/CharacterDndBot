package app.dnd.service.attack;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.dnd.model.telents.attacks.AttackAbility;
import app.dnd.repository.AttackAbilityRepository;

@Transactional
@Service
public class AttackAbilityService {

	@Autowired
	private AttackAbilityRepository attackAbilityRepository;
	
	public AttackAbility findByIdAndOwnerName(Long id, String ownerName) {
		
		Optional<AttackAbility> userOptional = attackAbilityRepository.findByUserIdAndOwnerName(id, ownerName);
		return userOptional.orElseGet(() -> AttackAbility.build(id, ownerName));
	}

	public void save(AttackAbility attackAbility) {
		attackAbilityRepository.save(attackAbility);
	}

	public void deleteByIdAndOwnerName(Long id, String ownerName) {
		attackAbilityRepository.deleteByUserIdAndOwnerName(id, ownerName);
	}

}

