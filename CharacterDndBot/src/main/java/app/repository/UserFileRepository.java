package app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.bot.model.user.UserFile;

@Repository
public interface UserFileRepository extends CrudRepository<UserFile, Long> {

}
