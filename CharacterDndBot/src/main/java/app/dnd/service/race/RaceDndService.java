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

	public RaceDnd findByUserIdAndOwnerName(Long id, String ownerName) {
		Optional< RaceDnd> userOptional = raceDndRepository.findByUserIdAndOwnerName(id, ownerName);
		return userOptional.orElseGet(() -> RaceDnd.build(id, ownerName));
	}

	public void save(RaceDnd raceDnd) {
		raceDndRepository.save(raceDnd);
	}

	public void deleteByIdAndOwnerName(Long id, String name) {
		raceDndRepository.deleteByUserIdAndOwnerName(id, name);
	}

}