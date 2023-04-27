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
		Optional<Memoirs> userOptional = memoirsRepository.findByUserIdAndOwnerName(id, ownerName);
		return userOptional.orElseGet(() -> Memoirs.build(id, ownerName));
	}

	public void save(Memoirs memoirs) {
		memoirsRepository.save(memoirs);
	}

	public void deleteByIdAndOwnerName(Long id, String name) {
		memoirsRepository.deleteByUserIdAndOwnerName(id, name);
	}

}
