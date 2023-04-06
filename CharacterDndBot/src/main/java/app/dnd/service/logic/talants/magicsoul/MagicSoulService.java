package app.dnd.service.logic.talants.magicsoul;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.dnd.model.ability.spells.MagicSoul;
import app.dnd.repository.MagicSoulRepository;

@Transactional
@Service
public class MagicSoulService {

	@Autowired
	private MagicSoulRepository magicSoulRepository;

	public MagicSoul getById(Long id) {
		Optional<MagicSoul> userOptional = magicSoulRepository.findById(id);
		if (userOptional.isPresent()) {
			return userOptional.get();
		} else {
			return MagicSoul.build(id);
		}
	}

	public void save(MagicSoul magicSoul) {
		magicSoulRepository.save(magicSoul);
	}

}
