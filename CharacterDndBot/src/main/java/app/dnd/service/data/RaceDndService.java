package app.dnd.service.data;

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

	public RaceDnd getById(Long id) {
		Optional< RaceDnd> userOptional = raceDndRepository.findById(id);
		if (userOptional.isPresent()) {
			return userOptional.get();
		} else {
			return RaceDnd.build(id);
		}
	}

	public void save(RaceDnd raceDnd) {
		raceDndRepository.save(raceDnd);
	}

}