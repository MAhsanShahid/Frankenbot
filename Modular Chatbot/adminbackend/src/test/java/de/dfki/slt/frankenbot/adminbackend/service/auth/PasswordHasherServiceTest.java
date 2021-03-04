package de.dfki.slt.frankenbot.adminbackend.service.auth;

import static org.junit.Assert.assertTrue;

import java.security.NoSuchAlgorithmException;

import org.junit.Test;

public class PasswordHasherServiceTest {

	/**
	 * Test if the password hashing algorithm works.
	 * @throws NoSuchAlgorithmException
	 */
	@Test
	public void testHash() throws NoSuchAlgorithmException {
		
		PasswordHasherService phs = new PasswordHasherService();
		
		byte[] salt = phs.getSalt();
		String password = "test_password";
		
		String hash1 = phs.get_SHA_512_SecurePassword(password, salt);

		String saltStr = phs.saltToString(salt);
		String hash2 = phs.get_SHA_512_SecurePassword(password, phs.saltToByte(saltStr));
		
		
		assertTrue(hash1.contentEquals(hash2));
	}
}
