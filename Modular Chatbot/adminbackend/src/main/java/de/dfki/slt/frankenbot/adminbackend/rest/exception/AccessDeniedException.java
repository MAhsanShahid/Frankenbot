package de.dfki.slt.frankenbot.adminbackend.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Access denied")

public class AccessDeniedException extends RuntimeException{

}
