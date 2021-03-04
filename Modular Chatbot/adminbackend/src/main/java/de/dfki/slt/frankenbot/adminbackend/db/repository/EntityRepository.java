package de.dfki.slt.frankenbot.adminbackend.db.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.dfki.slt.frankenbot.adminbackend.db.entities.Entity;

public interface EntityRepository extends CrudRepository<Entity, Long> {

	@Override
	List<Entity> findAll();

	Entity findOneById(Long userId);
}
