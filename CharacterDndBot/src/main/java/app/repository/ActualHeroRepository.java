package app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import app.bot.model.ActualHero;

@Repository
public interface ActualHeroRepository extends MongoRepository<ActualHero, Long> {

}
