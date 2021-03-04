package de.dfki.slt.frankenbot.adminbackend.export;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.dfki.slt.frankenbot.adminbackend.db.entities.DefaultModule;
import de.dfki.slt.frankenbot.adminbackend.db.entities.Intent;
import de.dfki.slt.frankenbot.adminbackend.db.entities.Utterance;
import de.dfki.slt.frankenbot.adminbackend.db.repository.IntentRepository;
import de.dfki.slt.frankenbot.adminbackend.db.repository.UtteranceRepository;
import de.dfki.slt.frankenbot.adminbackend.db.repository.DialogNodeTextAnswerRepository;

/**
 * Serialize a modules NLU definitions for RASA NLU. The output is a JSON like
 * 
 * {
    "rasa_nlu_data": {
        "common_examples": [
            {
                "text": "hi",
                "intent": "#hello",
                "entities": []
            },
            {
                "text": "hey",
                "intent": "#hello",
                "entities": []
            },
            {
                "text": "howdy",
                "intent": "#hello",
                "entities": []
            },
            ...
 
 * 
 * @author jan
 *
 */
@Service
public class NLUExporterService {

	@Autowired
	IntentRepository intentRepository;
	
	@Autowired
	UtteranceRepository utteranceRepository;
	
	@Autowired
	DialogNodeTextAnswerRepository dialogNodeTextAnswerRepository;

	public JSONObject exportRasaNLU(DefaultModule module) {
		JSONObject rasaNLU = new JSONObject();

		JSONArray rasaNLUTemp = new JSONArray();
		
//		for( DialogNode dialog : dialogNodeRepository.findByDefaultModule(module)) {
////			System.out.println(dialog.getName());
////			System.out.println(dialog.getDialogNodeIntentConditions().get(Count));
//
//		}
//		for(DialogNodeIntentCondition dialogNodeIntentCondition : dialogNodeIntentConditionRepository.findAll()) {
//			System.out.println("HERE");
//			System.out.println(dialogNodeIntentCondition.getIntent().getName());
//			System.out.println(dialogNodeIntentCondition.getIntent().getName());
//		}
//		for(DialogNodeTextAnswer dialogNodeTextAnswer : dialogNodeTextAnswerRepository.findAll()) {
//			System.out.println("HERE");
//			System.out.println(dialogNodeTextAnswer.getText());
//	
//			JSONObject temp = new JSONObject();
//			temp.put("text", dialogNodeTextAnswer.getText());
//			temp.put("entities", "[]");
//			temp.put("intent", "#" + dialogNodeTextAnswer.getNode().getName());
//		
//			rasaNLUTemp.put(temp);
//			System.out.println(rasaNLUTemp.toString(4));
//		}
//		rasaNLU.put("common_examples", rasaNLUTemp);
//		System.out.println(rasaNLU.toString(4));
		for(Intent intent : intentRepository.findByModule(module)) {

			List<Utterance> utterances = utteranceRepository.findByIntent(intent);
			for(Utterance utterance : utterances) {
				JSONObject temp = new JSONObject();
				temp.put("text", utterance.getText());
				temp.put("entities", "[]");
				temp.put("intent", "#" + utterance.getIntent().getName());
				rasaNLUTemp.put(temp);
			}
//			System.out.println(rasaNLUTemp.toString(4));
		}
		rasaNLU.put("common_examples", rasaNLUTemp);
		
		return rasaNLU;
	}
}
