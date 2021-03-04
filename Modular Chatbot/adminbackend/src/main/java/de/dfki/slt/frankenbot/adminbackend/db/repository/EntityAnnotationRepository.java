package de.dfki.slt.frankenbot.adminbackend.db.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.dfki.slt.frankenbot.adminbackend.db.entities.EntityAnnotation;

public interface EntityAnnotationRepository extends CrudRepository<EntityAnnotation, Long> {

	@Override
	List<EntityAnnotation> findAll();

	EntityAnnotation findOneById(Long entityId);
}
