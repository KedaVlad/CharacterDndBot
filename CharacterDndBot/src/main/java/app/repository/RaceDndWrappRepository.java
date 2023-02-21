package app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import app.dnd.dto.wrap.RaceDndWrapp;

@Repository
public interface RaceDndWrappRepository extends MongoRepository<RaceDndWrapp, String> {

	@Aggregation(pipeline = { "{ $group: { _id: '$raceName' } }", "{ $project: { raceName: '$_id', _id: 0 } }" })
	List<String> findDistinctRaceName();
	
	@Aggregation(pipeline = { "{ $match: { raceName: ?0 } }", "{ $group: { _id: '$subRace' } }", "{ $project: { subRace: '$_id', _id: 0 } }" })
	List<String> findDistinctSubRaceByRaceName(String raceName);
	
	@Aggregation(pipeline = { "{ $match: { raceName: { $eq: ?0 }, subRace: { $eq: ?1 } } }", "{ $group: { _id: '$information' } }","{ $project: { information: '$_id', _id: 0 } }", "{ $limit: 1 }" } )
	String findDistinctInformationByRaceNameAndSubRace(String raceName, String subRace);

	@Aggregation(pipeline = { "{ $match: { raceName: { $eq: ?0 }, subRace: { $eq: ?1 } } }", "{ $limit: 1 }" })
	RaceDndWrapp findByRaceNameAndSubRace(String raceName, String subRace);

}
