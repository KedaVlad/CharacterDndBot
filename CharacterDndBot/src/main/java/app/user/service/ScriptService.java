package app.user.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.player.model.act.SingleAct;
import app.user.model.Script;
import app.user.repository.ScriptRepository;

@Transactional
@Service
public class ScriptService {

	@Autowired
	private ScriptRepository scriptRepository;

	public Script getById(Long id) {
		Optional<Script> userOptional = scriptRepository.findById(id);
		if (userOptional.isPresent()) {
			return userOptional.get();
		} else {
			Script script = new Script();
			script.setId(id);
			script.getMainTree().add(SingleAct.builder().name(id + "").build());
			return script;
		}
	}

	public void save(Script script) {
		scriptRepository.save(script);
	}

}
