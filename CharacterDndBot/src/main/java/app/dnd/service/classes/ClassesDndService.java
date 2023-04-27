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
		Optional<ClassesDnd> userOptional = classesDndRepository.findByUserIdAndOwnerName(id,ownerName);
		return userOptional.orElseGet(() -> ClassesDnd.build(id, ownerName));
	}

	public void save(ClassesDnd classesDnd) {
		classesDndRepository.save(classesDnd);
	}

	public void deleteByIdAndOwnerName(Long id, String ownerName) {
		classesDndRepository.deleteByUserIdAndOwnerName(id, ownerName);
	}

}
