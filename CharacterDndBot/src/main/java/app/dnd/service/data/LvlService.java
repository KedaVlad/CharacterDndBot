package app.dnd.service.data;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.dnd.model.characteristics.Lvl;
import app.dnd.repository.LvlRepository;

@Transactional
@Service
public class LvlService {

	@Autowired
	private LvlRepository lvlRepository;

	public Lvl getById(Long id) {
		Optional<Lvl> userOptional = lvlRepository.findById(id);
		if (userOptional.isPresent()) {
			return userOptional.get();
		} else {
			return Lvl.create(id);
		}
	}

	public void save(Lvl lvl) {
		lvlRepository.save(lvl);
	}

}
