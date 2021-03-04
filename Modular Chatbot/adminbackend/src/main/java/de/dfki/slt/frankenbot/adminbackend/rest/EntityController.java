package de.dfki.slt.frankenbot.adminbackend.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.dfki.slt.frankenbot.adminbackend.db.entities.Entity;
import de.dfki.slt.frankenbot.adminbackend.db.repository.EntityRepository;
import javassist.NotFoundException;

@RestController
public class EntityController extends BaseController {

	@Autowired
	EntityRepository entityRepository;

	@PostMapping(BaseController.API_PREFIX + "/entity")
	public Entity createEntity(@RequestBody Entity entity) {
		entityRepository.save(entity);
		return entity;
	}

	@GetMapping(BaseController.API_PREFIX + "/entity")
	public List<Entity> listEntitys() {
		return entityRepository.findAll();
	}

	@PutMapping(BaseController.API_PREFIX + "/entity/{entityId}")
	public Entity updateEntity(@RequestBody Entity entity, @PathVariable Long entityId) throws NotFoundException {
		Entity existingEntity = entityRepository.findOneById(entityId);

		if (existingEntity == null) {
			throw new NotFoundException("Cannot find entity with id " + entityId.toString());
		}

		entityRepository.save(entity);
		return existingEntity;
	}

	@DeleteMapping(BaseController.API_PREFIX + "/entity/{entityId}")
	public Entity deleteEntity(@PathVariable Integer entityId) throws NotFoundException {
		Entity existingEntity = entityRepository.findOneById(entityId.longValue());
		if (existingEntity == null) {
			throw new NotFoundException("Cannot find entity with id " + entityId.toString());
		}

		entityRepository.delete(existingEntity);
		return existingEntity;
	}
}
