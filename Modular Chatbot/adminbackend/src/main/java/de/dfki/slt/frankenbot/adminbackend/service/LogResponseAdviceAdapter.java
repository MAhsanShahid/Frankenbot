package de.dfki.slt.frankenbot.adminbackend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@ControllerAdvice
public class LogResponseAdviceAdapter implements ResponseBodyAdvice<Object> {
	    
		Logger logger = LoggerFactory.getLogger(LogResponseAdviceAdapter.class);

	    @Override
	    public boolean supports(MethodParameter methodParameter,
	                            Class<? extends HttpMessageConverter<?>> aClass) {
	        return true;
	    }
	    
		@Override
		public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
				Class<? extends HttpMessageConverter<?>> selectedConverterType,
				org.springframework.http.server.ServerHttpRequest request,
				org.springframework.http.server.ServerHttpResponse response) {
			
			ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
			String bodyStr = null;
			try {
				bodyStr = objectMapper.writeValueAsString(body);
			} catch (JsonProcessingException e) {
				bodyStr = body.toString();
			}
			
			bodyStr = bodyStr.substring(0, Math.min(bodyStr.length(), 5000));
			
			logger.info("Request response: " + bodyStr);
			return body;

		}

}
