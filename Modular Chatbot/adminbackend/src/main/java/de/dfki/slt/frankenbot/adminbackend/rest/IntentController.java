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

import de.dfki.slt.frankenbot.adminbackend.db.entities.DefaultModule;
import de.dfki.slt.frankenbot.adminbackend.db.entities.Intent;
import de.dfki.slt.frankenbot.adminbackend.db.repository.DefaultModuleRepository;
import de.dfki.slt.frankenbot.adminbackend.db.repository.IntentRepository;
import de.dfki.slt.frankenbot.adminbackend.rest.exception.NotFoundException;
import de.dfki.slt.frankenbot.adminbackend.service.auth.AuthenticationStatus;

@RestController
public class IntentController extends BaseController {

	@Autowired
	IntentRepository intentRepository;
	
	@Autowired
	DefaultModuleRepository moduleRepository;
	
	@Autowired
	AuthenticationStatus authStatus;

	@PostMapping(BaseController.API_PREFIX + "/intent")
	public Intent createIntent(@RequestBody Intent intent) {
		intent.setCreatedBy(authStatus.getAuthenticatedUser());
		intentRepository.save(intent);
		return intent;
	}

	@GetMapping(BaseController.API_PREFIX + "/intent")
	public List<Intent> listIntents() {
		return intentRepository.findAll();
	}

	@PutMapping(BaseController.API_PREFIX + "/intent/{intentId}")
	public Intent updateIntent(@RequestBody Intent intent, @PathVariable Long intentId) throws NotFoundException {
		Intent existingIntent = intentRepository.findOneById(intentId);

		if (existingIntent == null) {
			throw new NotFoundException("Cannot find intent with id " + intentId.toString());
		}
		
		intent.setId(intentId);
		intentRepository.save(intent);
		return existingIntent;
	}

	@DeleteMapping(BaseController.API_PREFIX + "/intent/{intentId}")
	public Intent deleteIntent(@PathVariable Integer intentId) throws NotFoundException {
		Intent existingIntent = intentRepository.findOneById(intentId.longValue());
		if (existingIntent == null) {
			throw new NotFoundException("Cannot find intent with id " + intentId.toString());
		}

		intentRepository.delete(existingIntent);
		return existingIntent;
	}

	@GetMapping(BaseController.API_PREFIX + "/intent/by_module/{moduleId}")
	public List<Intent> listIntentsByModule(@PathVariable Integer moduleId) {
		DefaultModule module = moduleRepository.findOneById(moduleId.longValue());
		if( module == null ) {
			throw new NotFoundException("Cannot find module with id \"" + moduleId + "\"");
		}
		
		return intentRepository.findByModule(module);
	}
}
