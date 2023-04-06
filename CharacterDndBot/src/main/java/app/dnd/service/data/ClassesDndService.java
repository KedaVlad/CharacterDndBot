package app.dnd.service.data;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.dnd.model.hero.ClassesDnd;
import app.dnd.repository.ClassesDndRepository;

@Transactional
@Service
public class ClassesDndService {

	@Autowired
	private ClassesDndRepository classesDndRepository;

	public ClassesDnd getById(Long id) {
		Optional<ClassesDnd> userOptional = classesDndRepository.findById(id);
		if (userOptional.isPresent()) {
			return userOptional.get();
		} else {
			return ClassesDnd.build(id);
		}
	}

	public void save(ClassesDnd classesDnd) {
		classesDndRepository.save(classesDnd);
	}

}
