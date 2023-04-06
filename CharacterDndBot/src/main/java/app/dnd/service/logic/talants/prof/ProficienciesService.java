package app.dnd.service.logic.talants.prof;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.dnd.model.ability.proficiency.Proficiencies;
import app.dnd.repository.ProficienciesRepository;

@Transactional
@Service
public class ProficienciesService {

	@Autowired
	private ProficienciesRepository proficienciesRepository;

	public Proficiencies getById(Long id) {
		Optional<Proficiencies> userOptional = proficienciesRepository.findById(id);
		if (userOptional.isPresent()) {
			return userOptional.get();
		} else {
			return Proficiencies.build(id);
		}
	}

	public void save(Proficiencies proficiencies) {
		proficienciesRepository.save(proficiencies);
	}

}
