package de.dfki.slt.frankenbot.adminbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(DefaultConfiguration.class)
public class AdminBackendStarter {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(AdminBackendStarter.class, args);
		
//		CreateTestDataService data = context.getBean(CreateTestDataService.class);
//		data.createSimpleTestData();
	}
	
}
