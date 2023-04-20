package app.dnd.service.memoirs;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.dnd.model.ability.Memoirs;
import app.dnd.repository.MemoirsRepository;

@Transactional
@Service
public class MemoirsService {

	@Autowired
	private MemoirsRepository memoirsRepository;

	public Memoirs findByIdAndOwnerName(Long id, String ownerName)  {
		Optional<Memoirs> userOptional = memoirsRepository.findByIdAndOwnerName(id, ownerName);
		if (userOptional.isPresent()) {
			return userOptional.get();
		} else {
			return Memoirs.build(id, ownerName);
		}
	}

	public void save(Memoirs memoirs) {
		memoirsRepository.save(memoirs);
	}

	public void deleteByIdAndOwnerName(Long id, String name) {
		memoirsRepository.deleteByIdAndOwnerName(id, name);
	}

}
