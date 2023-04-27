package app.dnd.service.ability;

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

	public Ability findByIdAndOwnerName(Long id, String ownerName) {
		Optional<Ability> ability = abilityRepository.findByUserIdAndOwnerName(id, ownerName);
		return ability.orElseGet(() -> Ability.build(id, ownerName));
	}

	public void save(Ability characteristics) {
		abilityRepository.save(characteristics);
	}

	public void deleteByIdAndOwnerName(Long id, String name) {
		abilityRepository.deleteByUserIdAndOwnerName(id, name);
	}
	
	

}
