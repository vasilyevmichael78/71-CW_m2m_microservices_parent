package telran.accounting.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;



import static telran.security.constants.EndPointsConstants.*;

import java.util.Base64;

import javax.annotation.PostConstruct;


import telran.util.InvalidInputException;
@Component
public class AccountingComponent {
	static final Logger LOG = LoggerFactory.getLogger(AccountingComponent.class);
	RestTemplate restTemplate = new RestTemplate();
	@Value("${app.accounting.host}")
	String host;
	final static String  URL = "http://";
	@Value("${app.accounting.username:user}")
	String username;
	@Value ("${app.accounting.password}")
	String password;
	HttpHeaders headers = new HttpHeaders();
	HttpEntity<String> requestEntity;
	@PostConstruct
	public void setAuthorizationHeader() {
		LOG.debug("accounting component is loaded");
		String authHeader = "Basic " + getToken();
		headers.add("Authorization", authHeader);
		LOG.debug("password: {},user:{} header: {}", password,username, authHeader);
		requestEntity = new HttpEntity<String>(headers);
	}
private String getToken() {
		String userPassword = username + ":" + password;
		return Base64.getEncoder().encodeToString(userPassword.getBytes());
	}
String getRole(String username) {
	String role = getRequestResult(username, ACCOUNTS_ROLE_USERNAME);
	LOG.debug("role={}", role);
	return role;
}
String getPasswordHash(String username) {
	return getRequestResult(username, ACCOUNTS_HASH_USERNAME);
}
String getActivationTime(String username) {
	return getRequestResult(username, ACCOUNTS_TIME_USERNAME);
}
private String getRequestResult(String username, String type) {
	
	try {
		String fullURL = URL +  host + type + username;
		LOG.debug("Full URL {}", fullURL);
		ResponseEntity<String> response =
				restTemplate.exchange
				(fullURL,
						HttpMethod.GET, requestEntity, String.class);
		LOG.debug(response.getBody().toString());
		return response.getBody();
	} catch (Exception e) {
		throw new InvalidInputException(e.getMessage());
	}
}
}
