package telran.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import java.util.*;
@Configuration
public class SecurityConfigurater extends WebSecurityConfigurerAdapter {
@Value("${authorization:true}")
boolean authorization;
@Bean
PasswordEncoder getPasswordEncoder() {
	//return new BCryptPasswordEncoder();
	return PasswordEncoderFactories.createDelegatingPasswordEncoder(); 
}
@Autowired
private SecurityExceptionsHandler securityExceptionHandler;
	@Override
protected void configure(HttpSecurity httpSecurity) throws Exception {
	httpSecurity.httpBasic(); //will be used Http basic authorization
	httpSecurity.csrf().disable();
	//commented lines for canceling session ID
//	httpSecurity.sessionManagement()
//    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	//System.out.println(authorization);
	if (!authorization) {
		httpSecurity.authorizeRequests()
		.anyRequest().permitAll();
	} else {
		httpSecurity.exceptionHandling().authenticationEntryPoint(securityExceptionHandler)
		.accessDeniedHandler(securityExceptionHandler);
		httpSecurity.authorizeRequests()
		.antMatchers(HttpMethod.GET).authenticated();
		
		httpSecurity.authorizeRequests()
		.antMatchers(HttpMethod.POST).hasRole("ADMIN");
		httpSecurity.authorizeRequests()
		.antMatchers(HttpMethod.DELETE).hasRole("ADMIN");
		httpSecurity.authorizeRequests()
		.antMatchers(HttpMethod.PUT).hasRole("ADMIN");
	}
}
}
