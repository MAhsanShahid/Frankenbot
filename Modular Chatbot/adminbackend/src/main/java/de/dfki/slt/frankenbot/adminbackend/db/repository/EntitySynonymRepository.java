package de.dfki.slt.frankenbot.adminbackend.db.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.dfki.slt.frankenbot.adminbackend.db.entities.EntitySynonym;

public interface EntitySynonymRepository extends CrudRepository<EntitySynonym, Long> {

	@Override
	List<EntitySynonym> findAll();

	EntitySynonym findOneById(Long entitySynonymId);
}
