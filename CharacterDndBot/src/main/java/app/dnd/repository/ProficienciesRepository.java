package app.dnd.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import app.dnd.model.ability.proficiency.Proficiencies;

@Repository
public interface ProficienciesRepository extends MongoRepository<Proficiencies, Long> {

}
