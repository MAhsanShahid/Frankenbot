package de.dfki.slt.frankenbot.adminbackend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class DefaultConfiguration {

	@Bean
	public CommonsRequestLoggingFilter requestLoggingFilter() {
	    CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
	    loggingFilter.setIncludeClientInfo(true);
	    loggingFilter.setIncludeQueryString(true);
	    loggingFilter.setIncludePayload(true);
	    loggingFilter.setMaxPayloadLength(2000);
	    loggingFilter.setIncludeHeaders(false);
	    loggingFilter.setAfterMessagePrefix("REQUEST DATA : ");
	    return loggingFilter;
	}
}
