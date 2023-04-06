package app.dnd.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import app.dnd.model.hero.RaceDnd;

@Repository
public interface RaceDndRepository extends MongoRepository<RaceDnd, Long> {

}
