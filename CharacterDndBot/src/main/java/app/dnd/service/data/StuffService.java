package app.dnd.service.data;

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

	public Stuff getById(Long id) {
		Optional< Stuff> userOptional = stuffRepository.findById(id);
		if (userOptional.isPresent()) {
			return userOptional.get();
		} else {
			return Stuff.build(id);
		}
	}

	public void save(Stuff stuff) {
		stuffRepository.save(stuff);
	}

}