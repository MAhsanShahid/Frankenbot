package de.dfki.slt.frankenbot.adminbackend.db.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.dfki.slt.frankenbot.adminbackend.db.entities.DefaultModule;
import de.dfki.slt.frankenbot.adminbackend.db.entities.Intent;

public interface IntentRepository extends CrudRepository<Intent, Long> {

	@Override
	List<Intent> findAll();

	Intent findOneById(Long intentId);
	
	List<Intent> findByModule(DefaultModule module);
}
