package de.dfki.slt.frankenbot.adminbackend.rest;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import de.dfki.slt.frankenbot.adminbackend.db.entities.User;
import de.dfki.slt.frankenbot.adminbackend.db.repository.UserRepository;
import de.dfki.slt.frankenbot.adminbackend.service.auth.AuthenticationService;
import javassist.NotFoundException;

@RestController
public class UserController extends BaseController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	AuthenticationService authService;

	@PostMapping(BaseController.API_PREFIX + "/user")
	public User createUser(@RequestBody User user) throws NoSuchAlgorithmException {

		User existing = userRepository.findOneByEmail(user.getEmail());
		if (existing != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
		}

		authService.newUser(user);
		userRepository.save(user);
		return user;
	}

	@GetMapping(BaseController.API_PREFIX + "/user")
	public List<User> listUsers() {
		return userRepository.findAll();
	}

	@PutMapping(BaseController.API_PREFIX + "/user/{userId}")
	public User updateUser(@RequestBody User user, @PathVariable Long userId) throws NotFoundException {
		User existingUser = userRepository.findOneById(userId);

		if (existingUser == null) {
			throw new NotFoundException("Cannot find user with id " + userId.toString());
		}

		existingUser.setEmail(user.getEmail());
		existingUser.setName(user.getName());
		existingUser.setPassword(user.getPassword());
		authService.updateUser(existingUser);

		userRepository.save(existingUser);
		return existingUser;
	}

	@DeleteMapping(BaseController.API_PREFIX + "/user/{userId}")
	public User deleteUser(@PathVariable Integer userId) throws NotFoundException {
		User existingUser = userRepository.findOneById(userId.longValue());
		if (existingUser == null) {
			throw new NotFoundException("Cannot find user with id " + userId.toString());
		}

		userRepository.delete(existingUser);
		return existingUser;
	}
}
