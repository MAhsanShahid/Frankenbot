package de.dfki.slt.frankenbot.adminbackend.service.auth;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import de.dfki.slt.frankenbot.adminbackend.db.entities.AuthToken;
import de.dfki.slt.frankenbot.adminbackend.db.entities.User;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AuthenticationStatus {

	public enum State {
		AUTHENTICATED, NOT_AUTHENTICATED
	}

	State state = State.NOT_AUTHENTICATED;
	User authenticatedUser;
	AuthToken authenticatedToken;

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public User getAuthenticatedUser() {
		return authenticatedUser;
	}

	public void setAuthenticatedUser(User authenticatedUser) {
		this.authenticatedUser = authenticatedUser;
	}

	public AuthToken getAuthenticatedToken() {
		return authenticatedToken;
	}

	public void setAuthenticatedToken(AuthToken authenticatedToken) {
		this.authenticatedToken = authenticatedToken;
	}

}
