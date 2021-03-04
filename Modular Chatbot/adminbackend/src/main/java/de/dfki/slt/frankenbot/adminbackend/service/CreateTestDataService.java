package de.dfki.slt.frankenbot.adminbackend.service;

import java.util.ArrayList;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import de.dfki.slt.frankenbot.adminbackend.db.entities.AuthToken;
import de.dfki.slt.frankenbot.adminbackend.db.entities.Chatbot;
import de.dfki.slt.frankenbot.adminbackend.db.entities.DefaultModule;
import de.dfki.slt.frankenbot.adminbackend.db.entities.DialogNode;
import de.dfki.slt.frankenbot.adminbackend.db.entities.DialogNodeIntentCondition;
import de.dfki.slt.frankenbot.adminbackend.db.entities.DialogNodeTextAnswer;
import de.dfki.slt.frankenbot.adminbackend.db.entities.Entity;
import de.dfki.slt.frankenbot.adminbackend.db.entities.EntityAnnotation;
import de.dfki.slt.frankenbot.adminbackend.db.entities.EntitySynonym;
import de.dfki.slt.frankenbot.adminbackend.db.entities.EntityValue;
import de.dfki.slt.frankenbot.adminbackend.db.entities.Intent;
import de.dfki.slt.frankenbot.adminbackend.db.entities.User;
import de.dfki.slt.frankenbot.adminbackend.db.entities.Utterance;
import de.dfki.slt.frankenbot.adminbackend.db.repository.AuthTokenRepository;
import de.dfki.slt.frankenbot.adminbackend.db.repository.ChatbotRepository;
import de.dfki.slt.frankenbot.adminbackend.db.repository.DefaultModuleRepository;
import de.dfki.slt.frankenbot.adminbackend.db.repository.DialogNodeIntentConditionRepository;
import de.dfki.slt.frankenbot.adminbackend.db.repository.DialogNodeRepository;
import de.dfki.slt.frankenbot.adminbackend.db.repository.DialogNodeTextAnswerRepository;
import de.dfki.slt.frankenbot.adminbackend.db.repository.EntityAnnotationRepository;
import de.dfki.slt.frankenbot.adminbackend.db.repository.EntityRepository;
import de.dfki.slt.frankenbot.adminbackend.db.repository.EntitySynonymRepository;
import de.dfki.slt.frankenbot.adminbackend.db.repository.EntityValueRepository;
import de.dfki.slt.frankenbot.adminbackend.db.repository.IntentRepository;
import de.dfki.slt.frankenbot.adminbackend.db.repository.UserRepository;
import de.dfki.slt.frankenbot.adminbackend.db.repository.UtteranceRepository;

@Service
@Profile("create_sample_data")
public class CreateTestDataService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	DefaultModuleRepository defaultModuleRepository;

	@Autowired
	ChatbotRepository chatbotRepository;

	@Autowired
	IntentRepository intentRepository;;

	@Autowired
	UtteranceRepository utteranceRepository;

	@Autowired
	EntityRepository entityRepository;

	@Autowired
	EntityValueRepository entityValueRepository;

	@Autowired
	EntitySynonymRepository entitySynonymRepository;

	@Autowired
	EntityAnnotationRepository entityAnnotationRepository;

	@Autowired
	AuthTokenRepository tokenRepository;
	
	@Autowired
	DialogNodeRepository dialogNodeRepository;
	
	@Autowired
	DialogNodeTextAnswerRepository dialogNodeTextAnswerRepository;
	
	@Autowired
	DialogNodeIntentConditionRepository dialogNodeIntentConditionRepository;

	public static final String AUTH_TOKEN = "18e9d995-dd5f-49d9-8262-4b16a6f0c020";
	public static final String EMAIL = "test@test.de";
	public static final String PASSWORD = "geheim";

	public void deleteTestData() {
		userRepository.deleteAll();
		defaultModuleRepository.deleteAll();
		chatbotRepository.deleteAll();
		intentRepository.deleteAll();
		utteranceRepository.deleteAll();
		entityRepository.deleteAll();
		entityValueRepository.deleteAll();
		entitySynonymRepository.deleteAll();
		entityAnnotationRepository.deleteAll();
		tokenRepository.deleteAll();
	}
	
	@PostConstruct
	public void createSimpleTestData() {
		System.out.println("create sample data");
		User user = new User();
		user.setName("klaus");
		user.setPassword(
				"5201665ddd71fece58694f596e93383d9a5023c37f8ff18332957af05461f37ca6fbdf2d034fad9deba5511135ada179a5398b712dc3a1c00e567be7a5179127");
		user.setEmail(EMAIL);
		user.setSalt("0xeERVpI0EryTUZXe+k5YQ=="); // password is "geheim"
		userRepository.save(user);

		AuthToken token = new AuthToken();
		token.setToken(AUTH_TOKEN);
		token.setUser(user);
		token.setCreationDate(new Date());
		tokenRepository.save(token);

		Chatbot chatbot = new Chatbot();
		chatbot.setCreatedBy(user);
		chatbot.setName("Mein Chatbot");
		chatbotRepository.save(chatbot);

		DefaultModule module = new DefaultModule();
		module.setName("Mein Testmodul");
		module.setCreatedBy(user);
		defaultModuleRepository.save(module);

		chatbot.setModules(new ArrayList<DefaultModule>());
		chatbot.getModules().add(module);
		chatbotRepository.save(chatbot);

		Intent intent = new Intent();
		intent.setModule(module);
		intent.setCreatedBy(user);
		intent.setName("Begruessung");
		intent.setDescription("Begruessungen und andere Anreden");
		intentRepository.save(intent);

		Utterance utterance = new Utterance();
		utterance.setText("Guten morgen");
		utterance.setIntent(intent);
		utterance.setCreatedBy(user);
		utteranceRepository.save(utterance);

		utterance = new Utterance();
		utterance.setText("Moin moin Apfelkuchen");
		utterance.setIntent(intent);
		utterance.setCreatedBy(user);
		utteranceRepository.save(utterance);

		Entity entity = new Entity();
		entity.setName("Gebäck");
		entity.setModule(module);
		entity.setCreatedBy(user);
		entityRepository.save(entity);

		EntityValue apfelkuchen = new EntityValue();
		apfelkuchen.setEntity(entity);
		apfelkuchen.setValue("Apfelkuchen");
		apfelkuchen.setCreatedBy(user);
		entityValueRepository.save(apfelkuchen);

		EntityValue entityValue = new EntityValue();
		entityValue.setEntity(entity);
		entityValue.setValue("Erdbeertorte");
		entityValue.setCreatedBy(user);
		entityValueRepository.save(entityValue);

		EntitySynonym entitySynonym = new EntitySynonym();
		entitySynonym.setCreatedBy(user);
		entitySynonym.setEntityValue(entityValue);
		entitySynonym.setSynonym("Erdbeertarte");
		entitySynonymRepository.save(entitySynonym);

		EntityAnnotation entityAnnotation = new EntityAnnotation();
		entityAnnotation.setEntityValue(entityValue);
		entityAnnotation.setBeginIndex(10);
		entityAnnotation.setEndIndex(21);
		entityAnnotation.setCreatedBy(user);
		entityAnnotationRepository.save(entityAnnotation);
		
		DialogNode node = new DialogNode();
		node.setDefaultModule(module);
		node.setName("Greeting");
		node.setOrdering(0);
		dialogNodeRepository.save(node);
		
		DialogNode node2 = new DialogNode();
		node2.setDefaultModule(module);
		node2.setName("Tschuess");
		node2.setOrdering(1);
		dialogNodeRepository.save(node2);
		
		DialogNode child = new DialogNode();
		child.setName("Child of greeting");
		child.setDefaultModule(module);
		child.setParent(node);
		child.setOrdering(0);
		dialogNodeRepository.save(child);
		
		DialogNodeIntentCondition condition = new DialogNodeIntentCondition();
		condition.setIntent(intent);
		condition.setNode(node);
		dialogNodeIntentConditionRepository.save(condition);
		
		DialogNodeTextAnswer answer = new DialogNodeTextAnswer();
		answer.setNode(node);
		answer.setOrdering(0);
		answer.setText("Hallo, schön dich kennenzulernen!");
		dialogNodeTextAnswerRepository.save(answer);
	}
}
