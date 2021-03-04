package de.dfki.slt.frankenbot.adminbackend.db.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.dfki.slt.frankenbot.adminbackend.db.entities.DialogNodeTextAnswer;

public interface DialogNodeTextAnswerRepository extends CrudRepository<DialogNodeTextAnswer, Long> {
	@Override
	List<DialogNodeTextAnswer> findAll();
	DialogNodeTextAnswer findOneById(Long dialogNodeTextAnswerId);
}
