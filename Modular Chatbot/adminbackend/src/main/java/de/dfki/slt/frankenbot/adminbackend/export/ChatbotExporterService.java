package de.dfki.slt.frankenbot.adminbackend.export;

import java.util.List;

import javax.transaction.Transactional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.dfki.slt.frankenbot.adminbackend.db.entities.Chatbot;
import de.dfki.slt.frankenbot.adminbackend.db.entities.DefaultModule;
import de.dfki.slt.frankenbot.adminbackend.db.entities.DialogNode;
import de.dfki.slt.frankenbot.adminbackend.db.entities.DialogNodeIntentCondition;
import de.dfki.slt.frankenbot.adminbackend.db.entities.DialogNodeTextAnswer;
import de.dfki.slt.frankenbot.adminbackend.db.entities.Intent;
import de.dfki.slt.frankenbot.adminbackend.db.entities.Utterance;
import de.dfki.slt.frankenbot.adminbackend.db.repository.DialogNodeIntentConditionRepository;
import de.dfki.slt.frankenbot.adminbackend.db.repository.DialogNodeRepository;
import de.dfki.slt.frankenbot.adminbackend.db.repository.DialogNodeTextAnswerRepository;
import de.dfki.slt.frankenbot.adminbackend.db.repository.IntentRepository;
import de.dfki.slt.frankenbot.adminbackend.db.repository.UtteranceRepository;

/**
 * Convert a chatbot and its various components to json
 * 
 * @author jan
 *
 */
@Service
@Transactional
public class ChatbotExporterService {

	@Autowired
	IntentRepository intentRepository;
	
	@Autowired
	UtteranceRepository utteranceRepository;
	
	@Autowired
	DialogNodeRepository dialogNodeRepository;
	
	@Autowired
	DialogNodeTextAnswerRepository dialogNodeTextAnswerRepository;
	
	@Autowired
	DialogNodeIntentConditionRepository dialogNodeIntentConditionRepository;
	

	public JSONObject serializeChatbot(Chatbot chatbot) {
		JSONObject json = new JSONObject();
		
		json.put("name", chatbot.getName());
		json.put("type", "bot");
		json.put("welcome_message", "Hi, I am the test bot.");
		json.put("fallback_message", "I cant hear you. Can you please speak a bit louder?");
		
		// dialog manager
		JSONObject dialogManager = new JSONObject();
		json.put("dialog_manager", dialogManager);
		dialogManager.put("type", "max_activation_dialog_manager");
		JSONArray modules = new JSONArray();
		for (DefaultModule module : chatbot.getModules()) {
//			System.out.println(serializeDefaultModule(module).toString(4));
			modules.put(serializeDefaultModule(module));
		}
		dialogManager.put("modules", modules);
		
		return json;
	}

	private JSONObject serializeDefaultModule(DefaultModule module) {
		JSONObject json = new JSONObject();
		json.put("module_name", module.getName());
		json.put("type", "dialog_tree");		
		json.put("id", module.getId());
		JSONArray dialog_tree_list = new JSONArray();
		for (DialogNode dialog : dialogNodeRepository.findByDefaultModule(module)) {
			JSONObject dialog_tree = new JSONObject();
			JSONObject response_generator = new JSONObject();
			dialog_tree.put("type", "tree_node");
			dialog_tree.put("node_id", dialog.getId());
			dialog_tree.put("parent_node", formatDialogNode(dialog.getParent()));
			dialog_tree.put("intent_name", formatConditionIntent(dialog.getDialogNodeIntentConditions()));
			response_generator.put("type", "simple_response_generator");
			response_generator.put("mode", "sequential");
			JSONArray answersArray = new JSONArray();
			List<DialogNodeTextAnswer> answers = dialog.getDialogNodeTextAnswers();
			if( answers.size()>0) {
				answersArray.put(0,answers.get(0).getText());
			}
			response_generator.put("responses", answersArray);
						
			dialog_tree.put("response_generator", response_generator);
			dialog_tree_list.put(dialog_tree);
		}
		json.put("dialog_tree", dialog_tree_list);
		return json;
	}

	private JSONObject serializeIntent(Intent intent) {
		JSONObject json = new JSONObject();
		json.put("name", intent.getName());
		
		JSONArray utterances = new JSONArray();
		for( Utterance utterance : utteranceRepository.findByIntent(intent) ) {
			utterances.put(utterance.getText());
		}
		json.put("utterances", utterances);
		return json;
	}
	private JSONObject serializeDialog(DialogNode node) {
		JSONObject json = new JSONObject();
		json.put("name", node.getName());
		
		JSONArray utterances = new JSONArray();
		json.put("utterances", utterances);
		return json;
	}
	private String formatConditionIntent(List<DialogNodeIntentCondition> s) {

		if(s.size() > 0) {
			System.out.println(s);
			System.out.println(s.get(0));
			System.out.println(s.get(0).getIntent());
			System.out.println(s.get(0).getIntent().getName());
			return "#" + s.get(0).getIntent().getName();
		}
		return "";
	}
	private String formatDialogNode(DialogNode dialogNode) {
		if(dialogNode != null) {
			return String.valueOf(Math.toIntExact(dialogNode.getId()));
		}
		return "null";
		
	}
}


