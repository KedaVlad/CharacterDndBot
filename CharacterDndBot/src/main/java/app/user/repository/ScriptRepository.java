package app.user.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import app.user.model.Script;

@Repository
public interface ScriptRepository extends MongoRepository<Script, Long> {

}
