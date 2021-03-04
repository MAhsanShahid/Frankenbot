package de.dfki.slt.frankenbot.adminbackend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import de.dfki.slt.frankenbot.adminbackend.db.entities.AuthToken;
import de.dfki.slt.frankenbot.adminbackend.service.auth.AuthenticationService;

@RestController
public class AuthController extends BaseController {

	@Autowired
	AuthenticationService authService;
	
	@PostMapping(BaseController.API_PREFIX + "/auth_token")
	public AuthToken createToken(@RequestHeader("X-Auth-Email") String email, @RequestHeader("X-Auth-Password") String password) {
		return authService.createToken(email, password);
	}

}
