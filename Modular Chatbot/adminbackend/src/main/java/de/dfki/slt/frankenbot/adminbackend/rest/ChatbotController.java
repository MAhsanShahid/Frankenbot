package de.dfki.slt.frankenbot.adminbackend.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.dfki.slt.frankenbot.adminbackend.db.entities.Chatbot;
import de.dfki.slt.frankenbot.adminbackend.db.repository.ChatbotRepository;
import de.dfki.slt.frankenbot.adminbackend.service.auth.AuthenticationStatus;
import javassist.NotFoundException;

@RestController
public class ChatbotController extends BaseController {

	@Autowired
	ChatbotRepository chatbotRepository;

	@Autowired
	AuthenticationStatus authStatus;

	@PostMapping(BaseController.API_PREFIX + "/chatbot")
	public Chatbot createUser(@RequestBody Chatbot chatbot) {
		chatbot.setCreatedBy(authStatus.getAuthenticatedUser());
		chatbotRepository.save(chatbot);
		return chatbot;
	}

	@GetMapping(BaseController.API_PREFIX + "/chatbot")
	public List<Chatbot> listUsers() {
		return chatbotRepository.findAll();
	}

	@PutMapping(BaseController.API_PREFIX + "/chatbot/{chatbotId}")
	public Chatbot updateChatbot(@RequestBody Chatbot chatbot,
			@PathVariable Long chatbotId) throws NotFoundException {
		Chatbot existingChatbot = chatbotRepository.findOneById(chatbotId);

		if (existingChatbot == null) {
			throw new NotFoundException("Cannot find chatbot with id " + chatbotId.toString());
		}

		chatbot.setId(chatbotId);
		chatbotRepository.save(chatbot);
		return existingChatbot;
	}

	@DeleteMapping(BaseController.API_PREFIX + "/chatbot/{chatbotId}")
	public Chatbot deleteUser(@PathVariable Integer chatbotId) throws NotFoundException {
		Chatbot existingChatbot = chatbotRepository.findOneById(chatbotId.longValue());
		if (existingChatbot == null) {
			throw new NotFoundException("Cannot find chatbot with id " + chatbotId.toString());
		}

		chatbotRepository.delete(existingChatbot);
		return existingChatbot;
	}
}
