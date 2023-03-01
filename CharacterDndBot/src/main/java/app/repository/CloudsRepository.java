package app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import app.bot.model.user.Clouds;

@Repository
public interface CloudsRepository extends MongoRepository<Clouds, Long> {

}
