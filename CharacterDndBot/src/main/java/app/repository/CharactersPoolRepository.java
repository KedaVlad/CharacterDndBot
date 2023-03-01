package app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import app.bot.model.user.CharactersPool;

@Repository
public interface CharactersPoolRepository extends MongoRepository<CharactersPool, Long> {


}
