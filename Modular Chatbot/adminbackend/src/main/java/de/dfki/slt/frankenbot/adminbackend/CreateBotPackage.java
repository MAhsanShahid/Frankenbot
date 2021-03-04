package de.dfki.slt.frankenbot.adminbackend;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

import de.dfki.slt.frankenbot.adminbackend.db.entities.Chatbot;
import de.dfki.slt.frankenbot.adminbackend.db.repository.ChatbotRepository;
import de.dfki.slt.frankenbot.adminbackend.export.CreateBotPackageService;

@SpringBootApplication
@Import(DefaultConfiguration.class)
public class CreateBotPackage {

	public static void main(String[] args) throws IOException {
		ConfigurableApplicationContext context = SpringApplication.run(AdminBackendStarter.class, args);
		ChatbotRepository chatbotRepo = context.getBean(ChatbotRepository.class);
		CreateBotPackageService createBot = context.getBean(CreateBotPackageService.class);
		Chatbot chatbot = chatbotRepo.findAll().get(0);
		createBot.createZip(chatbot);
		context.stop();
	}
	
}
