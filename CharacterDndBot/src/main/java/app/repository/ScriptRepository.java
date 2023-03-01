package app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import app.bot.model.user.Script;

@Repository
public interface ScriptRepository extends MongoRepository<Script, Long> {

}
