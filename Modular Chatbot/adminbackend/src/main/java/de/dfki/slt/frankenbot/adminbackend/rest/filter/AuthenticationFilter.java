package de.dfki.slt.frankenbot.adminbackend.rest.filter;

import java.io.IOException;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.dfki.slt.frankenbot.adminbackend.db.entities.AuthToken;
import de.dfki.slt.frankenbot.adminbackend.db.repository.AuthTokenRepository;
import de.dfki.slt.frankenbot.adminbackend.rest.BaseController;
import de.dfki.slt.frankenbot.adminbackend.rest.exception.AccessDeniedException;
import de.dfki.slt.frankenbot.adminbackend.service.auth.AuthenticationStatus;
import de.dfki.slt.frankenbot.adminbackend.service.auth.AuthenticationStatus.State;

/**
 * This method hooks into every http request. it checks if the provided token is
 * valid and generally forbids access to most endpoints when the user is not
 * authenticated.
 * 
 * @author jan
 *
 */
@Component
public class AuthenticationFilter implements Filter {

	@Autowired
	AuthTokenRepository authTokenRepository;

	@Autowired
	AuthenticationStatus authStatus;

	private static class Endpoint {
		String path;

		enum Method {
			GET, POST, PUT, DELETE
		};

		Method method;

		public Endpoint(String path, Method method) {
			super();
			this.path = path;
			this.method = method;
		}

		public String getPath() {
			return path;
		}

		public String getMethod() {
			return method.toString();
		}
	}

	// all api endpoints in this set are available without authentication
	ArrayList<Endpoint> whitelist = new ArrayList<Endpoint>();

	@PostConstruct
	public void init() {
		whitelist.add(new Endpoint("/api/user", Endpoint.Method.POST));
		whitelist.add(new Endpoint("/api/auth_token", Endpoint.Method.POST));
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		boolean forbidRequest = false;
		if (request instanceof HttpServletRequest) {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			
			// apply authentication only to requests that start with /api
			if(httpRequest.getRequestURI().startsWith(BaseController.API_PREFIX)) {
				String tokenStr = httpRequest.getHeader("x-auth-token");
				if (tokenStr == null) {
					authStatus.setState(State.NOT_AUTHENTICATED);
				} else {
					AuthToken token = authTokenRepository.findOneByToken(tokenStr);
					if (token == null) {
						authStatus.setState(AuthenticationStatus.State.NOT_AUTHENTICATED);
					} else {
						authStatus.setState(AuthenticationStatus.State.AUTHENTICATED);
						authStatus.setAuthenticatedUser(token.getUser());
						authStatus.setAuthenticatedToken(token);	
					}
				}
				
				// some api endpoints are whitelisted, so they work with authentication
				// all other endpoints require authentication
				boolean isWhitelisted = false;
				for( Endpoint ep : whitelist ) {
					if( httpRequest.getRequestURI().contentEquals(ep.getPath()) && httpRequest.getMethod().contentEquals(ep.getMethod())) {
						isWhitelisted = true;
					}
				}
				
				if( !isWhitelisted && authStatus.getState() == State.NOT_AUTHENTICATED) {
					forbidRequest = true;
					HttpServletResponse httpResponse = (HttpServletResponse) response;
					AccessDeniedException e = new AccessDeniedException();
					httpResponse.setStatus(HttpStatus.FORBIDDEN.value());
					//httpResponse.getWriter().write(convertObjectToJson(e));
					//throw new AccessDeniedException();
				}
			}
		}
		
		if( !forbidRequest) {
			chain.doFilter(request, response);			
		}
	}
	
    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

}
