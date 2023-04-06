package app.user.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import app.user.model.Trash;

@Repository
public interface TrashRepository extends MongoRepository<Trash, Long> {

}