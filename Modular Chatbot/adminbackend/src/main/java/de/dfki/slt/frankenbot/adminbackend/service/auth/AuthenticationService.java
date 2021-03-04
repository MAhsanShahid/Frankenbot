package de.dfki.slt.frankenbot.adminbackend.service.auth;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.dfki.slt.frankenbot.adminbackend.db.entities.AuthToken;
import de.dfki.slt.frankenbot.adminbackend.db.entities.User;
import de.dfki.slt.frankenbot.adminbackend.db.repository.AuthTokenRepository;
import de.dfki.slt.frankenbot.adminbackend.db.repository.UserRepository;

@Service
public class AuthenticationService {

	@Autowired
	PasswordHasherService passwordHasherService;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AuthTokenRepository authTokenRepository;
	
	/**
	 * When user is created, this method creates the salt and hashes the user password
	 * @param user
	 * @throws NoSuchAlgorithmException 
	 */
	public void newUser(User user) throws NoSuchAlgorithmException {
		byte[] salt = passwordHasherService.getSalt();
		user.setSalt(passwordHasherService.saltToString(salt));
		String hash = passwordHasherService.get_SHA_512_SecurePassword(user.getPassword(), salt);
		user.setPassword(hash);
	}
	
	/**
	 * When user password is updated, this methods converts it from cleartext to hash
	 * @param user
	 */
	public void updateUser(User user) {
		byte[] salt = passwordHasherService.saltToByte(user.getSalt());
		String hash = passwordHasherService.get_SHA_512_SecurePassword(user.getPassword(), salt);
		user.setPassword(hash);
	}
	
	/**
	 * Create a new token for username and password
	 * @param email
	 * @param password
	 * @return
	 * @throws AuthenticationFailedException
	 */
	public AuthToken createToken(String email, String password) throws AuthenticationFailedException{
		User user = userRepository.findOneByEmail(email);
		byte[] salt = passwordHasherService.saltToByte(user.getSalt());
		String hash = passwordHasherService.get_SHA_512_SecurePassword(password, salt);
		
		if(!user.getPassword().contentEquals(hash)) {
			throw new AuthenticationFailedException();
		}
		
		AuthToken token = new AuthToken();
		token.setToken(UUID.randomUUID().toString());
		token.setCreationDate(new Date());
		token.setUser(user);
		authTokenRepository.save(token);
		return token;
	}
	
}
