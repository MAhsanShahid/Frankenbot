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
import de.dfki.slt.frankenbot.adminbackend.db.repository.DefaultModuleRepository;
import de.dfki.slt.frankenbot.adminbackend.service.auth.AuthenticationStatus;
import javassist.NotFoundException;

@RestController
public class DefaultModuleController extends BaseController {

	@Autowired
	DefaultModuleRepository defaultModuleRepository;

	@Autowired
	AuthenticationStatus authStatus;

	@PostMapping(BaseController.API_PREFIX + "/defaultmodule")
	public DefaultModule createUser(@RequestBody DefaultModule defaultModule) {
		defaultModule.setCreatedBy(authStatus.getAuthenticatedUser());
		defaultModuleRepository.save(defaultModule);
		return defaultModule;
	}

	@GetMapping(BaseController.API_PREFIX + "/defaultmodule")
	public List<DefaultModule> listUsers() {
		return defaultModuleRepository.findAll();
	}

	@PutMapping(BaseController.API_PREFIX + "/defaultmodule/{id}")
	public DefaultModule updateDefaultModule(@RequestBody DefaultModule defaultModule,
			@PathVariable Long id) throws NotFoundException {
		DefaultModule existingDefaultModule = defaultModuleRepository.findOneById(id);

		if (existingDefaultModule == null) {
			throw new NotFoundException("Cannot find defaultModule with id " + id.toString());
		}

		defaultModule.setId(id);
		defaultModuleRepository.save(defaultModule);
		return existingDefaultModule;
	}

	@DeleteMapping(BaseController.API_PREFIX + "/defaultmodule/{id}")
	public DefaultModule deleteUser(@PathVariable Integer id) throws NotFoundException {
		DefaultModule existingDefaultModule = defaultModuleRepository.findOneById(id.longValue());
		if (existingDefaultModule == null) {
			throw new NotFoundException("Cannot find defaultModule with id " + id.toString());
		}

		defaultModuleRepository.delete(existingDefaultModule);
		return existingDefaultModule;
	}
}
