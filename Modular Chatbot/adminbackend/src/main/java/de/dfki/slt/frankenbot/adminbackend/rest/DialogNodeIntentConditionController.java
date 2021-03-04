package de.dfki.slt.frankenbot.adminbackend.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.dfki.slt.frankenbot.adminbackend.db.entities.DialogNodeIntentCondition;
import de.dfki.slt.frankenbot.adminbackend.db.repository.DialogNodeIntentConditionRepository;
import javassist.NotFoundException;

@RestController
public class DialogNodeIntentConditionController extends BaseController {

	@Autowired
	DialogNodeIntentConditionRepository dialogNodeIntentConditionRepository;
	

	@PostMapping(BaseController.API_PREFIX + "/dialog/intentcondition")
	public DialogNodeIntentCondition createUser(@RequestBody DialogNodeIntentCondition dialogNodeIntentCondition) {
		dialogNodeIntentConditionRepository.save(dialogNodeIntentCondition);
		return dialogNodeIntentCondition;
	}

	@GetMapping(BaseController.API_PREFIX + "/dialog/intentcondition")
	public List<DialogNodeIntentCondition> listUsers() {
		return dialogNodeIntentConditionRepository.findAll();
	}

	@PutMapping(BaseController.API_PREFIX + "/dialog/intentcondition/{dialogNodeIntentConditionId}")
	public DialogNodeIntentCondition updateDialogNodeIntentCondition(@RequestBody DialogNodeIntentCondition dialogNodeIntentCondition,
			@PathVariable Long dialogNodeIntentConditionId) throws NotFoundException {
		DialogNodeIntentCondition existingDialogNodeIntentCondition = dialogNodeIntentConditionRepository.findOneById(dialogNodeIntentConditionId);

		if (existingDialogNodeIntentCondition == null) {
			throw new NotFoundException("Cannot find dialog node intent with id " + dialogNodeIntentConditionId.toString());
		}

		dialogNodeIntentCondition.setId(dialogNodeIntentConditionId);
		dialogNodeIntentConditionRepository.save(dialogNodeIntentCondition);
		return existingDialogNodeIntentCondition;
	}

	@DeleteMapping(BaseController.API_PREFIX + "/dialog/intentcondition/{dialogNodeIntentConditionId}")
	public DialogNodeIntentCondition deleteUser(@PathVariable Integer dialogNodeIntentConditionId) throws NotFoundException {
		DialogNodeIntentCondition existingDialogNodeIntentCondition = dialogNodeIntentConditionRepository.findOneById(dialogNodeIntentConditionId.longValue());
		if (existingDialogNodeIntentCondition == null) {
			throw new NotFoundException("Cannot find dialog node intent with id " + dialogNodeIntentConditionId.toString());
		}

		dialogNodeIntentConditionRepository.delete(existingDialogNodeIntentCondition);
		return existingDialogNodeIntentCondition;
	}

}
