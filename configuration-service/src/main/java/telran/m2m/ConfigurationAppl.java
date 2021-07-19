package telran.m2m;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;

import telran.m2m.service.ConfigurationService;

@SpringBootApplication
public class ConfigurationAppl {

	public static void main(String[] args) throws JsonProcessingException {
		ConfigurableApplicationContext ctx = SpringApplication.run(ConfigurationAppl.class);
		ConfigurationService configurationService
		= ctx.getBean(ConfigurationService.class);

		configurationService.sendInitialConfiguration();
		
	}

}