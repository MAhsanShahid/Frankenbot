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

import de.dfki.slt.frankenbot.adminbackend.db.entities.EntityValue;
import de.dfki.slt.frankenbot.adminbackend.db.repository.EntityValueRepository;
import javassist.NotFoundException;

@RestController
public class EntityValueController extends BaseController {

	@Autowired
	EntityValueRepository evRepository;

	@PostMapping(BaseController.API_PREFIX + "/entity_value")
	public EntityValue createEntityValue(@RequestBody EntityValue entityValue) {
		evRepository.save(entityValue);
		return entityValue;
	}

	@GetMapping(BaseController.API_PREFIX + "/entity_value")
	public List<EntityValue> listEntityValues() {
		return evRepository.findAll();
	}

	@PutMapping(BaseController.API_PREFIX + "/entity_value/{entityValueId}")
	public EntityValue updateEntityValue(@RequestBody EntityValue entityValue, @PathVariable Long entityValueId) throws NotFoundException {
		EntityValue existingEntityValue = evRepository.findOneById(entityValueId);

		if (existingEntityValue == null) {
			throw new NotFoundException("Cannot find entityValue with id " + entityValueId.toString());
		}

		entityValue.setId(entityValueId);
		evRepository.save(entityValue);
		return existingEntityValue;
	}

	@DeleteMapping(BaseController.API_PREFIX + "/entity_value/{entityValueId}")
	public EntityValue deleteEntityValue(@PathVariable Integer entityValueId) throws NotFoundException {
		EntityValue existingEntityValue = evRepository.findOneById(entityValueId.longValue());
		if (existingEntityValue == null) {
			throw new NotFoundException("Cannot find entityValue with id " + entityValueId.toString());
		}

		evRepository.delete(existingEntityValue);
		return existingEntityValue;
	}
}
