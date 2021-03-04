package de.dfki.slt.frankenbot.adminbackend.service.auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Access denied")
public class AuthenticationFailedException extends RuntimeException{

}
