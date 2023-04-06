package app.dnd.service.data;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.dnd.model.characteristics.Hp;
import app.dnd.repository.HpRepository;

@Transactional
@Service
public class HpService {

	@Autowired
	private HpRepository hpRepository;

	public Hp getById(Long id) {
		Optional<Hp> userOptional = hpRepository.findById(id);
		if (userOptional.isPresent()) {
			return userOptional.get();
		} else {
			return Hp.build(id);
		}
	}

	public void save(Hp hp) {
		hpRepository.save(hp);
	}

}

