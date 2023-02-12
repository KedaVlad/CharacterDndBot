package com.dnd.CharacterDndBot.datacontrol;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnd.CharacterDndBot.service.bot.user.User;


@Service
@Transactional
public class UserFileService {

	@Autowired
	private UserFileRepository userFileRepository;

	public void save(User user) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(user);
		oos.flush();

		byte[] data = baos.toByteArray();

		UserFile userFile = new UserFile();
		userFile.setId(user.getId());
		userFile.setData(data);
		userFileRepository.save(userFile);
	}

	public User getUserById(Long id) throws IOException, ClassNotFoundException {
		Optional<UserFile> userOptional = userFileRepository.findById(id);
		if (userOptional.isPresent()) {
			byte[] data = userOptional.get().getData();
			ByteArrayInputStream bais = new ByteArrayInputStream(data);
			ObjectInputStream ois = new ObjectInputStream(bais);
			return (User) ois.readObject();
		} else {
			save(new User(id));
			return getUserById(id);
		}
	}

}


