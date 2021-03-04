package de.dfki.slt.frankenbot.adminbackend.db.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.dfki.slt.frankenbot.adminbackend.db.entities.User;

public interface UserRepository extends CrudRepository<User, Long> {

	@Override
	List<User> findAll();

	User findOneById(Long userId);
	
	User findOneByEmail(String email);
}
