package de.dfki.slt.frankenbot.adminbackend.db.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.dfki.slt.frankenbot.adminbackend.db.entities.Intent;
import de.dfki.slt.frankenbot.adminbackend.db.entities.Utterance;

public interface UtteranceRepository extends CrudRepository<Utterance, Long> {

	@Override
	List<Utterance> findAll();

	Utterance findOneById(Long utteranceId);
	
	List<Utterance> findByIntent(Intent intent);
}
