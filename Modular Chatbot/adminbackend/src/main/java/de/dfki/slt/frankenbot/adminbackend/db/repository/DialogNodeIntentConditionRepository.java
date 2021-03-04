package de.dfki.slt.frankenbot.adminbackend.db.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.dfki.slt.frankenbot.adminbackend.db.entities.DialogNodeIntentCondition;

public interface DialogNodeIntentConditionRepository extends CrudRepository<DialogNodeIntentCondition, Long> {

	@Override
	List<DialogNodeIntentCondition> findAll();
	DialogNodeIntentCondition findOneById(Long dialogNodeConditionId);
}

