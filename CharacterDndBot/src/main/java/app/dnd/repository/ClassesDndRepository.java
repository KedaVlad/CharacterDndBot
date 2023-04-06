package app.dnd.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import app.dnd.model.hero.ClassesDnd;

@Repository
public interface ClassesDndRepository extends MongoRepository<ClassesDnd, Long> {

}