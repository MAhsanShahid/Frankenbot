package de.dfki.slt.frankenbot.adminbackend.export;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.dfki.slt.frankenbot.adminbackend.db.entities.Chatbot;
import de.dfki.slt.frankenbot.adminbackend.db.entities.DefaultModule;
import de.dfki.slt.frankenbot.adminbackend.db.repository.ChatbotRepository;
import de.dfki.slt.frankenbot.adminbackend.db.repository.DefaultModuleRepository;

/**
 * This class creates a ZIP archive with all data to start a chatbot.
 * @author jan
 */
@Service
public class CreateBotPackageService {

	@Autowired
	NLUExporterService nLUExporterService;
	
	@Autowired
	ChatbotRepository chatbotrepository;
	
	@Autowired
    DefaultModuleRepository defaultModuleRepository;
	
	public File createZip(Chatbot chatbot) throws IOException {
		File outputfile = File.createTempFile("frankenbot-package-", ".zip");
		
		// escape name
		String chatbotName = chatbot.getName();
		chatbotName = chatbotName.toLowerCase().replaceAll("\\W", "-");
		
		ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(outputfile)));
		ZipEntry config = new ZipEntry(chatbotName + "/config_spacy.yaml");
		zos.putNextEntry(config);
		InputStream configInfo = CreateBotPackageService.class.getResourceAsStream("/rasa-pipelines/config_spacy.yaml"); 
		int length;
		byte[] buffer = new byte[1024];
		while ((length = configInfo.read(buffer)) > 0) {
		    zos.write(buffer, 0, length);
}
		
		for(DefaultModule module : chatbot.getModules()) {
			ZipEntry chatbotFile = new ZipEntry(chatbotName + "/" + module.getName());
			zos.putNextEntry(chatbotFile);
			String chatbotJson = nLUExporterService.exportRasaNLU(module).toString(4);
			zos.write(chatbotJson.getBytes());
		}
		
		zos.close();
		
		return outputfile;
	}
}
