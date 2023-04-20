package app.dnd.service.lvl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.dnd.model.ability.Lvl;
import app.dnd.repository.LvlRepository;

@Transactional
@Service
public class LvlService {

	@Autowired
	private LvlRepository lvlRepository;

	public Lvl findByIdAndOwnerName(Long id, String ownerName) {
		Optional<Lvl> userOptional = lvlRepository.findByIdAndOwnerName(id, ownerName);
		if (userOptional.isPresent()) {
			return userOptional.get();
		} else {
			return Lvl.create(id, ownerName);
		}
	}

	public void save(Lvl lvl) {
		lvlRepository.save(lvl);
	}

	public void deleteByIdAndOwnerName(Long id, String name) {
		lvlRepository.deleteByIdAndOwnerName(id, name);
	}

}
