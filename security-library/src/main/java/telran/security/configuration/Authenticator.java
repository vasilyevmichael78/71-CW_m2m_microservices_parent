
package telran.security.configuration;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
@Configuration
public class Authenticator implements UserDetailsService {
	static Logger LOG = LoggerFactory.getLogger(Authenticator.class);
@Value("${admin_name:admin}")
String adminName;
@Value("${admin_password}")
String adminPassword;
@Value("${user_password}")
String userPassword;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	LOG.debug("User {} is trying to log in", username);
	String password = username.equals(adminName) ? adminPassword :
		userPassword;
	if (password == null || password.isEmpty()) {
		LOG.error("password is not defined in environment");
	} else {
		LOG.debug("password: {}",password);
	}
	
	
	String role = username.equals(adminName) ? "ROLE_ADMIN" :
		"ROLE_USER";
	
	
		return new User(username, "{noop}" + password,
				AuthorityUtils
				.createAuthorityList(role));
	}

}
