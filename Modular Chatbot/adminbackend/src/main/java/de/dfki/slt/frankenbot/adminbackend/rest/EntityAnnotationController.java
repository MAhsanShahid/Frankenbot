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

import de.dfki.slt.frankenbot.adminbackend.db.entities.EntityAnnotation;
import de.dfki.slt.frankenbot.adminbackend.db.repository.EntityAnnotationRepository;
import javassist.NotFoundException;

@RestController
public class EntityAnnotationController extends BaseController {

	@Autowired
	EntityAnnotationRepository entityAnnotationRepository;

	@PostMapping(BaseController.API_PREFIX + "/entity_annotation")
	public EntityAnnotation createEntityAnnotation(@RequestBody EntityAnnotation entityAnnotation) {
		entityAnnotationRepository.save(entityAnnotation);
		return entityAnnotation;
	}

	@GetMapping(BaseController.API_PREFIX + "/entity_annotation")
	public List<EntityAnnotation> listEntityAnnotations() {
		return entityAnnotationRepository.findAll();
	}

	@PutMapping(BaseController.API_PREFIX + "/entity_annotation/{entityAnnotationId}")
	public EntityAnnotation updateEntityAnnotation(@RequestBody EntityAnnotation entityAnnotation, @PathVariable Long entityAnnotationId) throws NotFoundException {
		EntityAnnotation existingEntityAnnotation = entityAnnotationRepository.findOneById(entityAnnotationId);

		if (existingEntityAnnotation == null) {
			throw new NotFoundException("Cannot find entityAnnotation with id " + entityAnnotationId.toString());
		}
		
		entityAnnotation.setId(entityAnnotationId);
		entityAnnotationRepository.save(entityAnnotation);
		return existingEntityAnnotation;
	}

	@DeleteMapping(BaseController.API_PREFIX + "/entity_annotation/{entityAnnotationId}")
	public EntityAnnotation deleteEntityAnnotation(@PathVariable Integer entityAnnotationId) throws NotFoundException {
		EntityAnnotation existingEntityAnnotation = entityAnnotationRepository.findOneById(entityAnnotationId.longValue());
		if (existingEntityAnnotation == null) {
			throw new NotFoundException("Cannot find entityAnnotation with id " + entityAnnotationId.toString());
		}

		entityAnnotationRepository.delete(existingEntityAnnotation);
		return existingEntityAnnotation;
	}
}
