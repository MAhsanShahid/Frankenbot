package de.dfki.slt.frankenbot.adminbackend.jsonexporter;

import java.io.File;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import de.dfki.slt.frankenbot.adminbackend.AdminBackendStarter;
import de.dfki.slt.frankenbot.adminbackend.db.entities.Chatbot;
import de.dfki.slt.frankenbot.adminbackend.db.repository.ChatbotRepository;
import de.dfki.slt.frankenbot.adminbackend.db.repository.DefaultModuleRepository;
import de.dfki.slt.frankenbot.adminbackend.export.ChatbotExporterService;
import de.dfki.slt.frankenbot.adminbackend.export.CreateBotPackageService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=AdminBackendStarter.class)
@ActiveProfiles("create_sample_data")
//@Sql(scripts = "classpath:db/simple-data.sql")
public class JSONExporterTest {

	@Autowired
	ChatbotRepository chatbotRepo;
	
	@Autowired
	CreateBotPackageService exporter;
	
	@Autowired
	ChatbotExporterService JsonExporter;
	
	@Autowired
	ChatbotRepository chatbotRepository;
	
	@Autowired
	DefaultModuleRepository defaultModuleRepository;
	
//	@Test
//	public void test() throws JSONException {
//		assertTrue(chatbotRepo.findAll().size()>0);
//		
//		JSONObject json = exporter.serializeChatbot(chatbotRepo.findAll().get(0));
//		System.out.println(json.toString(4));
//	}
	
//	@Test
//	public void testRasaExport() throws JSONException {
//		DefaultModule module = defaultModuleRepository.findAll().get(0);
//		JSONObject json = rasaJsonExporter.exportRasaNLU(module);
//		System.out.println(json.toString(4));
//	}
	@Test
	public void testRasaExport() throws JSONException {
		Chatbot c = chatbotRepository.findAll().get(0);
		JSONObject json = JsonExporter.serializeChatbot(c);
		System.out.println(json.toString(4));
	}
	public void testCreateZip() throws JSONException, IOException {
		Chatbot c = chatbotRepository.findAll().get(0);
		File fileHere = exporter.createZip(c);
		System.out.println(fileHere.getAbsolutePath());
	}
	
}
