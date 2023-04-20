package app.dnd.service.hp;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.dnd.model.Refreshable.Time;
import app.dnd.model.ability.Hp;
import app.dnd.repository.HpRepository;

@Transactional
@Service
public class HpService {

	@Autowired
	private HpRepository hpRepository;

	public Hp findByIdAndOwnerName(Long id, String ownerName) {
		Optional<Hp> userOptional = hpRepository.findByIdAndOwnerName(id, ownerName);
		if (userOptional.isPresent()) {
			return userOptional.get();
		} else {
			return Hp.build(id, ownerName);
		}
	}

	public void save(Hp hp) {
		hpRepository.save(hp);
	}

	public void deleteByIdAndOwnerName(Long id, String name) {
		hpRepository.deleteByIdAndOwnerName(id, name);
	}

	public void refresh(Long id, String name, Time time) {
		Hp hp = findByIdAndOwnerName(id, name);
		hp.refresh(time);
		hpRepository.save(hp);
	}

}

