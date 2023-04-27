package app.bot.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.bot.model.user.ActualHero;
import app.bot.repository.ActualHeroRepository;

@Transactional
@Service
public class ActualHeroService {

	@Autowired
	private ActualHeroRepository actualHeroRepository;

	public ActualHero getById(Long id) {
		Optional<ActualHero> userOptional = actualHeroRepository.findById(id);
		if (userOptional.isPresent()) {
			return userOptional.get();
		} else {
			ActualHero actualHero = new ActualHero();
			actualHero.setId(id);
			actualHero.setCloudsWorked(new ArrayList<>());
			actualHero.setReadyToGame(false);
			actualHeroRepository.save(actualHero);
			return actualHero;
		}
	}
	
	public void save(ActualHero actualHero) {
		actualHeroRepository.save(actualHero);
	}

}

