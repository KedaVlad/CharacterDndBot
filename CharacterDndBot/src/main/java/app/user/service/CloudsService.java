package app.user.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.user.model.Clouds;
import app.user.repository.CloudsRepository;

@Transactional
@Service
public class CloudsService {

	@Autowired
	private CloudsRepository cloudsRepository;

	public Clouds getById(Long id) {
		Optional<Clouds> userOptional = cloudsRepository.findById(id);
		if (userOptional.isPresent()) {
			return userOptional.get();
		} else {
			Clouds clouds = new Clouds();
			clouds.setId(id);
			return clouds;
		}
	}

	public void save(Clouds clouds) {
		cloudsRepository.save(clouds);
	}

}
