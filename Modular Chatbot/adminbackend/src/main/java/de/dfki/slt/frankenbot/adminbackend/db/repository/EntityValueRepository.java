package de.dfki.slt.frankenbot.adminbackend.db.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.dfki.slt.frankenbot.adminbackend.db.entities.EntityValue;

public interface EntityValueRepository extends CrudRepository<EntityValue, Long> {

	@Override
	List<EntityValue> findAll();

	EntityValue findOneById(Long entityValue);
}
