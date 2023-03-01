package app.bot.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.bot.model.user.CharactersPool;
import app.repository.CharactersPoolRepository;

@Transactional
@Service
public class CharactersPoolService {

	@Autowired
	private CharactersPoolRepository charactersPoolRepository;
	
	public CharactersPool getById(Long id) {
		Optional<CharactersPool> userOptional = charactersPoolRepository.findById(id);
		if (userOptional.isPresent()) {
			return userOptional.get();
		} else {
			CharactersPool charactersPool = new CharactersPool();
			charactersPool.setId(id);
			return charactersPool;
		}
	}
	
	public void save(CharactersPool charactersPool) {
		charactersPoolRepository.save(charactersPool);
	}
	
}
