package app.dnd.service.classes;

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

	public ClassesDnd findByIdAndOwnerName(Long id, String ownerName) {
		Optional<ClassesDnd> userOptional = classesDndRepository.findById(id);
		if (userOptional.isPresent()) {
			return userOptional.get();
		} else {
			return ClassesDnd.build(id, ownerName);
		}
	}

	public void save(ClassesDnd classesDnd) {
		classesDndRepository.save(classesDnd);
	}

	public void deleteByIdAndOwnerName(Long id, String ownerName) {
		classesDndRepository.deleteByIdAndOwnerName(id, ownerName);
	}

}
