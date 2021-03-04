package de.dfki.slt.frankenbot.adminbackend.rest;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.dfki.slt.frankenbot.adminbackend.db.entities.Chatbot;
import de.dfki.slt.frankenbot.adminbackend.db.entities.DefaultModule;
import de.dfki.slt.frankenbot.adminbackend.db.repository.ChatbotRepository;
import de.dfki.slt.frankenbot.adminbackend.db.repository.DefaultModuleRepository;
import de.dfki.slt.frankenbot.adminbackend.export.ChatbotExporterService;
import de.dfki.slt.frankenbot.adminbackend.export.NLUExporterService;
import de.dfki.slt.frankenbot.adminbackend.rest.exception.NotFoundException;

@RestController
public class ChatbotExporterController extends BaseController{

	@Autowired
	ChatbotExporterService exporter;
	
	@Autowired
	ChatbotRepository chatbotRepository;
	
	@Autowired
	DefaultModuleRepository moduleRepository;
	
	@Autowired
	NLUExporterService rasaExporter;
	
	@RequestMapping(
		method = RequestMethod.GET,
		value = BaseController.API_PREFIX + "/export/chatbot/{chatbotId}",
		produces = "application/json"
	)
	public String exportChatbot(@PathVariable Long chatbotId) {
		Chatbot chatbot = chatbotRepository.findOneById(chatbotId);
		if( chatbot == null ) {
			throw new NotFoundException("Cannot find chatbot with id \"" + chatbotId + "\"");
		}
		JSONObject json = exporter.serializeChatbot(chatbot);
		return json.toString(4);
	}

	@RequestMapping(
			method = RequestMethod.GET,
			value = BaseController.API_PREFIX + "/export/nlu/{moduleId}",
			produces = "application/json"
	)
	public String exportRasaNLU(HttpServletResponse response, @PathVariable Long moduleId) {
		DefaultModule module = moduleRepository.findOneById(moduleId);
		if( module == null) {
			throw new NotFoundException("Cannot find module with id \"" + moduleId + "\"");			
		}
		JSONObject json = rasaExporter.exportRasaNLU(module);
		response.setContentType("application/json");
		return json.toString(4);
	}
}
