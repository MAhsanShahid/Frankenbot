package de.dfki.slt.frankenbot.adminbackend.rest;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.dfki.slt.frankenbot.adminbackend.db.entities.DefaultModule;
import de.dfki.slt.frankenbot.adminbackend.db.entities.DialogNode;
import de.dfki.slt.frankenbot.adminbackend.db.repository.DefaultModuleRepository;
import de.dfki.slt.frankenbot.adminbackend.db.repository.DialogNodeRepository;
import javassist.NotFoundException;

@RestController
public class DialogNodeController extends BaseController {

	@Autowired
	DialogNodeRepository dialogNodeRepository;

	@Autowired
	DefaultModuleRepository moduleRepository;
	
	@Autowired
	HttpServletRequest request;

	private DialogNode readNode(String jsonStr) throws JsonMappingException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		DialogNode node = objectMapper.readValue(jsonStr, DialogNode.class);
//		JSONObject json = new JSONObject(jsonStr);
		return node;
	}
	
	@PostMapping(BaseController.API_PREFIX + "/dialog/node")
	public DialogNode createNode(@RequestBody String body) throws IOException {
		DialogNode node = readNode(body);
		node = dialogNodeRepository.save(node);
		return node;
	}

	@GetMapping(BaseController.API_PREFIX + "/dialog/node")
	public List<DialogNode> listNodes() {
		return dialogNodeRepository.findAll();
	}

	@GetMapping(BaseController.API_PREFIX + "/dialog/node/{dialogNodeId}")
	public DialogNode getNode(@PathVariable Long dialogNodeId)
			throws NotFoundException {
		DialogNode node = dialogNodeRepository.findOneById(dialogNodeId);

		if (node == null) {
			throw new NotFoundException("Cannot find dialog node with id " + dialogNodeId.toString());
		}
		
		return node;
	}
	
	@PutMapping(BaseController.API_PREFIX + "/dialog/node/{dialogNodeId}")
	public DialogNode updateDialogNode(@RequestBody String body, @PathVariable Long dialogNodeId)
			throws NotFoundException, JsonMappingException, JsonProcessingException {
		DialogNode existingDialogNode = dialogNodeRepository.findOneById(dialogNodeId);

		JSONObject json = new JSONObject(body);

		if (existingDialogNode == null) {
			throw new NotFoundException("Cannot find dialog node with id " + dialogNodeId.toString());
		}
		DialogNode dialogNode = readNode(body);
		if(!json.has("defaultModule")) {
			dialogNode.setDefaultModule(existingDialogNode.getDefaultModule());
		}
		if(!json.has("dialogNodeIntentConditions")) {
			dialogNode.setDialogNodeIntentConditions(existingDialogNode.getDialogNodeIntentConditions());
		}
		if(!json.has("dialogNodeTextAnswers")) {
			dialogNode.setDialogNodeTextAnswers(existingDialogNode.getDialogNodeTextAnswers());
		}
		if(!json.has("parent")) {
			dialogNode.setParent(existingDialogNode.getParent());
		}
		dialogNode.setId(dialogNodeId);
		dialogNodeRepository.save(dialogNode);
		return existingDialogNode;
	}

	@DeleteMapping(BaseController.API_PREFIX + "/dialog/node/{dialogNodeId}")
	public DialogNode deleteNode(@PathVariable Integer dialogNodeId) throws NotFoundException {
		DialogNode existingDialogNode = dialogNodeRepository.findOneById(dialogNodeId.longValue());
		if (existingDialogNode == null) {
			throw new NotFoundException("Cannot find dialog intent condition with id " + dialogNodeId.toString());
		}

		dialogNodeRepository.delete(existingDialogNode);
		return existingDialogNode;
	}

	@GetMapping(BaseController.API_PREFIX + "/dialog/node/by_module/{moduleId}")
	public List<DialogNode> listNodesByModule(@PathVariable Integer moduleId) throws NotFoundException {
		DefaultModule module = moduleRepository.findOneById(moduleId.longValue());
		if (module == null) {
			throw new NotFoundException("Cannot find module with id \"" + moduleId + "\"");
		}

		List<DialogNode> nodes = dialogNodeRepository.findByDefaultModule(module);
		// delete all nodes from the flat list that are nested to avoid duplicate content
		for( int i=0; i<nodes.size(); i++){
			DialogNode node = nodes.get(i);
			if(node.getParent() != null) {
				nodes.remove(i);
				i--;
			}
		}
		return nodes;
	}
}
