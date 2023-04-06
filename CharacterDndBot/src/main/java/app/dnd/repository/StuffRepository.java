package app.dnd.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import app.dnd.model.stuffs.Stuff;

@Repository
public interface StuffRepository extends MongoRepository<Stuff, Long> {

}
