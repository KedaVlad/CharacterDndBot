package app.dnd.service.race;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.dnd.model.hero.RaceDnd;
import app.dnd.repository.RaceDndRepository;

@Transactional
@Service
public class RaceDndService {

	@Autowired
	private RaceDndRepository raceDndRepository;

	public RaceDnd findByIdAndOwnerName(Long id, String ownerName) {
		Optional< RaceDnd> userOptional = raceDndRepository.findById(id);
		if (userOptional.isPresent()) {
			return userOptional.get();
		} else {
			return RaceDnd.build(id,ownerName);
		}
	}

	public void save(RaceDnd raceDnd) {
		raceDndRepository.save(raceDnd);
	}

	public void deleteByIdAndOwnerName(Long id, String name) {
		raceDndRepository.deleteByIdAndOwnerName(id, name);
	}

}