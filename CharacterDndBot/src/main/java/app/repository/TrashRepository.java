package app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import app.bot.model.user.Trash;

@Repository
public interface TrashRepository extends MongoRepository<Trash, Long> {

}