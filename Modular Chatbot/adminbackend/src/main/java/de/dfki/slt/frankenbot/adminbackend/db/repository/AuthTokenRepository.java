package de.dfki.slt.frankenbot.adminbackend.db.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.dfki.slt.frankenbot.adminbackend.db.entities.AuthToken;

public interface AuthTokenRepository extends CrudRepository<AuthToken, Long> {

	@Override
	List<AuthToken> findAll();

	AuthToken findOneByToken(String token);
}
