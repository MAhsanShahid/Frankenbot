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

import de.dfki.slt.frankenbot.adminbackend.db.entities.DialogNodeTextAnswer;
import de.dfki.slt.frankenbot.adminbackend.db.repository.DialogNodeTextAnswerRepository;
import javassist.NotFoundException;

@RestController
public class DialogNodeTextAnswerController extends BaseController {

	@Autowired
	DialogNodeTextAnswerRepository dialogNodeTextAnswerRepository;

	@PostMapping(BaseController.API_PREFIX + "/dialog/textanswer")
	public DialogNodeTextAnswer createUser(@RequestBody DialogNodeTextAnswer dialogNodeTextAnswer) {
		dialogNodeTextAnswerRepository.save(dialogNodeTextAnswer);
		return dialogNodeTextAnswer;
	}

	@GetMapping(BaseController.API_PREFIX + "/dialog/textanswer")
	public List<DialogNodeTextAnswer> listUsers() {
		return dialogNodeTextAnswerRepository.findAll();
	}

	@PutMapping(BaseController.API_PREFIX + "/dialog/textanswer/{dialogNodeTextAnswerId}")
	public DialogNodeTextAnswer updateDialogNodeTextAnswer(@RequestBody DialogNodeTextAnswer dialogNodeTextAnswer,
			@PathVariable Long dialogNodeTextAnswerId) throws NotFoundException {
		DialogNodeTextAnswer existingDialogNodeTextAnswer = dialogNodeTextAnswerRepository.findOneById(dialogNodeTextAnswerId);

		if (existingDialogNodeTextAnswer == null) {
			throw new NotFoundException("Cannot find dialog node text answer with id " + dialogNodeTextAnswerId.toString());
		}

		dialogNodeTextAnswer.setId(dialogNodeTextAnswerId);
		dialogNodeTextAnswerRepository.save(dialogNodeTextAnswer);
		return existingDialogNodeTextAnswer;
	}

	@DeleteMapping(BaseController.API_PREFIX + "/dialog/textanswer/{dialogNodeTextAnswerId}")
	public DialogNodeTextAnswer deleteUser(@PathVariable Integer dialogNodeTextAnswerId) throws NotFoundException {
		DialogNodeTextAnswer existingDialogNodeTextAnswer = dialogNodeTextAnswerRepository.findOneById(dialogNodeTextAnswerId.longValue());
		if (existingDialogNodeTextAnswer == null) {
			throw new NotFoundException("Cannot find dialog node text answer with id " + dialogNodeTextAnswerId.toString());
		}

		dialogNodeTextAnswerRepository.delete(existingDialogNodeTextAnswer);
		return existingDialogNodeTextAnswer;
	}
}
