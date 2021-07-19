package telran.m2m;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class GeneratorAppl {

	private static final long GENERATOR_TIMEOUT = 60_0000;

	public static void main(String[] args) throws InterruptedException {
		ConfigurableApplicationContext ctx = SpringApplication.run(GeneratorAppl.class, args);
		Thread.sleep(GENERATOR_TIMEOUT);
		ctx.close();

	}

}
