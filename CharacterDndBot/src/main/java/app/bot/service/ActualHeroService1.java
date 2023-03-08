package app.bot.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.bot.model.ActualHero;
import app.repository.ActualHeroRepository;

@Transactional
@Service
public class ActualHeroService1 {

	@Autowired
	private ActualHeroRepository actualHeroRepository;

	public ActualHero getById(Long id) {
		Optional<ActualHero> userOptional = actualHeroRepository.findById(id);
		if (userOptional.isPresent()) {
			return userOptional.get();
		} else {
			ActualHero actualHero = new ActualHero();
			actualHero.setId(id);
			return actualHero;
		}
	}
	
	public void save(ActualHero actualHero) {
		actualHeroRepository.save(actualHero);
	}

}

