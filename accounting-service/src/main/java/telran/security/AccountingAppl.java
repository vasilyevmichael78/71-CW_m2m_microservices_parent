package telran.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ComponentScan({"telran"})
@PropertySource({"classpath:application.properties",
	"classpath:application-${spring.profiles.active}.properties"})
public class AccountingAppl {

	public static void main(String[] args) {
		SpringApplication.run(AccountingAppl.class, args);

	}

}
