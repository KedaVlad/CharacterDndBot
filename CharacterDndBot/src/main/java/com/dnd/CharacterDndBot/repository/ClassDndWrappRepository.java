package com.dnd.CharacterDndBot.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.dnd.CharacterDndBot.dnd.dto.wrap.ClassDndWrapp;

@Repository
public interface ClassDndWrappRepository extends MongoRepository<ClassDndWrapp, String> {
    
	@Aggregation(pipeline = { "{ $group: { _id: '$className' } }", "{ $project: { className: '$_id', _id: 0 } }" })
	List<String> findDistinctClassName();
    
	@Aggregation(pipeline = { "{ $match: { className: ?0 } }", "{ $group: { _id: '$archetype' } }", "{ $project: { archetype: '$_id', _id: 0 } }" })
    List<String> findDistinctArchetypeByClassName(String className);
    
    @Aggregation(pipeline = { "{ $match: { className: { $eq: ?0 }, archetype: { $eq: ?1 } } }", "{ $group: { _id: '$information' } }","{ $project: { information: '$_id', _id: 0 } }", "{ $limit: 1 }" } )
    String findDistinctInformationByClassNameAndArchetype(String className, String archetype);
    
    @Aggregation(pipeline = { "{ $match: { className: { $eq: ?0 }, archetype: { $eq: ?1 } } }", "{ $limit: 1 }" })
    ClassDndWrapp findByClassNameAndArchetype(String className, String archetype);
}