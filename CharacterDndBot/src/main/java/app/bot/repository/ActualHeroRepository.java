package app.bot.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import app.bot.model.user.ActualHero;

@Repository
public interface ActualHeroRepository extends MongoRepository<ActualHero, Long> {

}
