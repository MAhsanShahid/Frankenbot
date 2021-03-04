package de.dfki.slt.frankenbot.adminbackend.service.auth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.stereotype.Service;

/**
 * hash a password with sha 512 and salt
 * 
 * from here https://howtodoinjava.com/security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/
 * @author jan
 *
 */
@Service
public class PasswordHasherService {

	public byte[] getSalt() throws NoSuchAlgorithmException {
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		byte[] salt = new byte[16];
		sr.nextBytes(salt);
		return salt;
	}

	public String get_SHA_512_SecurePassword(String passwordToHash, byte[] salt) {
		String generatedPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(salt);
			byte[] bytes = md.digest(passwordToHash.getBytes());
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return generatedPassword;
	}
	
	public String saltToString(byte[] salt) {
		return Base64.getEncoder().encodeToString(salt);
	}
	
	public byte[] saltToByte(String salt) {
		return Base64.getDecoder().decode(salt);
	}
	
}
