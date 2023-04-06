package app.dnd.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import app.user.model.CharactersPool;

@Repository
public interface CharactersPoolRepository extends MongoRepository<CharactersPool, Long> {

}
