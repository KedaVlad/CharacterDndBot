package app.dnd.service.stuff;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.dnd.model.stuffs.Stuff;
import app.dnd.repository.StuffRepository;

@Transactional
@Service
public class StuffService {

	@Autowired
	private StuffRepository stuffRepository;

	public Stuff findByIdAndOwnerName(Long id, String ownerName) {
		Optional<Stuff> userOptional = stuffRepository.findByUserIdAndOwnerName(id, ownerName);
		return userOptional.orElseGet(() -> Stuff.build(id, ownerName));
	}

	public void save(Stuff stuff) {
		stuffRepository.save(stuff);
	}

	public void deleteByIdAndOwnerName(Long id, String name) {
		stuffRepository.deleteByUserIdAndOwnerName(id, name);
	}

}