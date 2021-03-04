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

import de.dfki.slt.frankenbot.adminbackend.db.entities.Intent;
import de.dfki.slt.frankenbot.adminbackend.db.entities.Utterance;
import de.dfki.slt.frankenbot.adminbackend.db.repository.IntentRepository;
import de.dfki.slt.frankenbot.adminbackend.db.repository.UtteranceRepository;
import javassist.NotFoundException;

@RestController
public class UtteranceController extends BaseController {

	@Autowired
	UtteranceRepository utteranceRepository;
	
	@Autowired
	IntentRepository intentRepository;

	@PostMapping(BaseController.API_PREFIX + "/utterance")
	public Utterance createUtterance(@RequestBody Utterance utterance) {
		utteranceRepository.save(utterance);
		return utterance;
	}

	@GetMapping(BaseController.API_PREFIX + "/utterance")
	public List<Utterance> listUtterances() {
		return utteranceRepository.findAll();
	}

	@PutMapping(BaseController.API_PREFIX + "/utterance/{utteranceId}")
	public Utterance updateUtterance(@RequestBody Utterance utterance, @PathVariable Long utteranceId) throws NotFoundException {
		Utterance existingUtterance = utteranceRepository.findOneById(utteranceId);

		if (existingUtterance == null) {
			throw new NotFoundException("Cannot find utterance with id " + utteranceId.toString());
		}

		utterance.setId(utteranceId);
		utteranceRepository.save(utterance);
		return existingUtterance;
	}

	@DeleteMapping(BaseController.API_PREFIX + "/utterance/{utteranceId}")
	public Utterance deleteUtterance(@PathVariable Integer utteranceId) throws NotFoundException {
		Utterance existingUtterance = utteranceRepository.findOneById(utteranceId.longValue());
		if (existingUtterance == null) {
			throw new NotFoundException("Cannot find utterance with id " + utteranceId.toString());
		}

		utteranceRepository.delete(existingUtterance);
		return existingUtterance;
	}
	
	@GetMapping(BaseController.API_PREFIX + "/utterance/by_intent/{intentId}")
	public List<Utterance> listUtterancesByIntent(@PathVariable Integer intentId) {
		Intent intent = intentRepository.findOneById(intentId.longValue());
		if( intent == null ) {
			throw new RuntimeException("Cannot find intent with id \"" + intentId + "\"");
		}
		
		return utteranceRepository.findByIntent(intent);
	}
}
