package app.dnd.service.talants;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.dnd.model.telents.spells.MagicSoul;
import app.dnd.repository.MagicSoulRepository;

@Transactional
@Service
public class MagicSoulService {

	@Autowired
	private MagicSoulRepository magicSoulRepository;

	public MagicSoul findByIdAndOwnerName(Long id, String ownerName) {
		Optional<MagicSoul> userOptional = magicSoulRepository.findByUserIdAndOwnerName(id, ownerName);
		return userOptional.orElseGet(() -> MagicSoul.build(id, ownerName));
	}

	public void save(MagicSoul magicSoul) {
		magicSoulRepository.save(magicSoul);
	}

	public void deleteByIdAndOwnerName(Long id, String name) {
		magicSoulRepository.deleteByUserIdAndOwnerName(id, name);
	}

}
