package telran.accounting.components;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatorComponent implements UserDetailsService{
	static final Logger LOG = LoggerFactory.getLogger(AuthenticatorComponent.class);
@Autowired
AccountingComponent accounting;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LOG.debug("username: {}", username);
		String passwordHash = accounting.getPasswordHash(username);
		
		
		if (passwordHash == null) {
			LOG.error("passwordHash id null");
			throw new UsernameNotFoundException(username);
			
			
		}
		String role = accounting.getRole(username);
		LOG.debug("role: {}", role);
		return new User(username, passwordHash,
				AuthorityUtils.createAuthorityList("ROLE_" + role));
	}

}
