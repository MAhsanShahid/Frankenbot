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

import de.dfki.slt.frankenbot.adminbackend.db.entities.EntitySynonym;
import de.dfki.slt.frankenbot.adminbackend.db.repository.EntitySynonymRepository;
import javassist.NotFoundException;

@RestController
public class EntitySynonymController extends BaseController {

	@Autowired
	EntitySynonymRepository esRepository;

	@PostMapping(BaseController.API_PREFIX + "/entity_synonym")
	public EntitySynonym createEntitySynonym(@RequestBody EntitySynonym entitySynonym) {
		esRepository.save(entitySynonym);
		return entitySynonym;
	}

	@GetMapping(BaseController.API_PREFIX + "/entity_synonym")
	public List<EntitySynonym> listEntitySynonyms() {
		return esRepository.findAll();
	}

	@PutMapping(BaseController.API_PREFIX + "/entity_synonym/{entitySynonymId}")
	public EntitySynonym updateEntitySynonym(@RequestBody EntitySynonym entitySynonym, @PathVariable Long entitySynonymId) throws NotFoundException {
		EntitySynonym existingEntitySynonym = esRepository.findOneById(entitySynonymId);

		if (existingEntitySynonym == null) {
			throw new NotFoundException("Cannot find entitySynonym with id " + entitySynonymId.toString());
		}

		entitySynonym.setId(entitySynonymId);
		esRepository.save(entitySynonym);
		return entitySynonym;
	}

	@DeleteMapping(BaseController.API_PREFIX + "/entity_synonym/{entitySynonymId}")
	public EntitySynonym deleteEntitySynonym(@PathVariable Integer entitySynonymId) throws NotFoundException {
		EntitySynonym existingEntitySynonym = esRepository.findOneById(entitySynonymId.longValue());
		if (existingEntitySynonym == null) {
			throw new NotFoundException("Cannot find entitySynonym with id " + entitySynonymId.toString());
		}

		esRepository.delete(existingEntitySynonym);
		return existingEntitySynonym;
	}
}
