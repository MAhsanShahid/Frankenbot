package de.dfki.slt.frankenbot.adminbackend.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "EMAIL_EXISTS")
public class UserExistsException extends RuntimeException{

}
