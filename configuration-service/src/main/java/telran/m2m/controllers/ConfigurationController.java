package telran.m2m.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigurationController {
	static Logger LOG = LoggerFactory.getLogger(ConfigurationController.class);
	@Autowired
	Source source;
	@PostMapping("/configuration")
String updateConfiguration(@RequestBody String config) {
	source.output().send(MessageBuilder.withPayload(config).build());
	LOG.info("Update configuration; new configuration is {}", config);
	return config;
}
}
