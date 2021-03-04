package de.dfki.slt.frankenbot.adminbackend.integration;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import de.dfki.slt.frankenbot.adminbackend.DefaultConfiguration;

@SpringBootApplication
@ComponentScan("de.dfki.slt.frankenbot.adminbackend")
@PropertySource("classpath:/conf/integration_test.properties")
@Import(DefaultConfiguration.class)
public class IntegrationTestConfiguration {

}
