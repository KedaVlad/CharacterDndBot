package app.dnd.service.data;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.dnd.model.characteristics.Memoirs;
import app.dnd.repository.MemoirsRepository;

@Transactional
@Service
public class MemoirsService {

	@Autowired
	private MemoirsRepository memoirsRepository;

	public Memoirs getById(Long id) {
		Optional<Memoirs> userOptional = memoirsRepository.findById(id);
		if (userOptional.isPresent()) {
			return userOptional.get();
		} else {
			return Memoirs.build(id);
		}
	}

	public void save(Memoirs memoirs) {
		memoirsRepository.save(memoirs);
	}

}
