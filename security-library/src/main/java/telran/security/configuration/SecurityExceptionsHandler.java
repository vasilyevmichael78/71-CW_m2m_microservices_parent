package telran.security.configuration;

import java.io.IOException;
import java.nio.file.attribute.UserPrincipal;
import java.security.Principal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class SecurityExceptionsHandler implements AccessDeniedHandler, AuthenticationEntryPoint {
@Autowired
static private final Logger LOG = LoggerFactory.getLogger(SecurityExceptionsHandler.class);
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		String description = "Authentication error";
		log(request, description);
	
		response.setStatus(401);

	}

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		String description = "Authorization error";
		log(request, description);
		
		response.setStatus(403);

	}

	private void log(HttpServletRequest request, String description) {
		String user =  getUser(request);
		LOG.error("{}, user: {}  ", description, user) ;
	}

	private String getUser(HttpServletRequest request) {
		Principal principle = request.getUserPrincipal();
		String user = principle == null ? "unknown" : principle.getName();
		return user;
	}

}
