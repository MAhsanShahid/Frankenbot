package de.dfki.slt.frankenbot.adminbackend.db.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.dfki.slt.frankenbot.adminbackend.db.entities.Chatbot;

public interface ChatbotRepository extends CrudRepository<Chatbot, Long> {

	@Override
	List<Chatbot> findAll();

	Chatbot findOneById(Long chatbotId);
}
