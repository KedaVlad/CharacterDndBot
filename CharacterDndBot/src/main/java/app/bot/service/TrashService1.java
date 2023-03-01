package app.bot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.bot.model.user.Trash;
import app.repository.TrashRepository;

@Transactional
@Service
public class TrashService1 {
	
	@Autowired
	private TrashRepository trashRepository;
	
	public Trash getById(Long id) {
		Optional<Trash> userOptional = trashRepository.findById(id);
		if (userOptional.isPresent()) {
			return userOptional.get();
		} else {
			Trash trash = new Trash();
			trash.setId(id);
			return trash;
		}
	}
	
	public void save(Trash trash) {
		trashRepository.save(trash);
	}
	
	public void addToCircle(Long id, List<Integer> newTrash) {
		Trash trash = getById(id);
		trash.getCircle().addAll(newTrash);
		trashRepository.save(trash);
	}
	
	public void addToCircle(Long id, Integer newTrash) {
		Trash trash = getById(id);
		trash.getCircle().add(newTrash);
		trashRepository.save(trash);
	}
	
	public List<Integer> trashThrowOut(Long id) {
		Trash trash = getById(id);
		List<Integer> throwed = new ArrayList<>();
		throwed.addAll(trash.getCircle());
		trash.getCircle().clear();
		save(trash);
		return throwed;
}
}
