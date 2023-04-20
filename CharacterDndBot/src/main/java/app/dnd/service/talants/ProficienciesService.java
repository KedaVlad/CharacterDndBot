package app.dnd.service.talants;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.dnd.model.telents.proficiency.Proficiencies;
import app.dnd.repository.ProficienciesRepository;

@Transactional
@Service
public class ProficienciesService {

	@Autowired
	private ProficienciesRepository proficienciesRepository;

	public Proficiencies findByIdAndOwnerName(Long id, String ownerName) {
		Optional<Proficiencies> userOptional = proficienciesRepository.findByIdAndOwnerName(id, ownerName);
		if (userOptional.isPresent()) {
			return userOptional.get();
		} else {
			return Proficiencies.build(id, ownerName);
		}
	}

	public void save(Proficiencies proficiencies) {
		proficienciesRepository.save(proficiencies);
	}

	public void deleteByIdAndOwnerName(Long id, String name) {
		proficienciesRepository.deleteByIdAndOwnerName(id, name);
	}

}
