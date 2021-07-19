package telran.security.controllers;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import telran.security.api.AccountingService;
import telran.security.dto.*;
import telran.util.InvalidInputException;
import telran.util.NotFoundException;

import static telran.security.constants.EndPointsConstants.*;

import java.time.LocalDateTime;
@RestController
public class AccountingController {
	static final Logger LOG = LoggerFactory.getLogger(AccountingController.class);
	@Autowired
	AccountingService accountingService;
	@PostMapping(ACCOUNTS)
	boolean addAccount(@RequestBody AccountDto accountDto) {
		boolean res = accountingService.addAccount(accountDto);
		if (!res) {
			throw new InvalidInputException
			(String.format("Account username: %s already exists",
					accountDto.username) );
		}
		return res;
	}
	@DeleteMapping(ACCOUNTS_USERNAME )
	boolean removeAccount(@PathVariable String username) {
		boolean res = accountingService.removeAccount(username);
		if (!res) {
			throw new NotFoundException(String.format("Account username: %s not found",
					username));
		}
		return res;
	}
	@PutMapping(ACCOUNTS_USERNAME)
	boolean updatePassword(@PathVariable String username, @RequestBody String password) {
		boolean res = accountingService.updatePassword(username, password);
		if (!res) {
			throw new NotFoundException(String.format("Account username: %s not found",
					username));
		}
		return res;
	}
	@GetMapping(ACCOUNTS_HASH_USERNAME)
	String getPasswordHash(@PathVariable String username) {
		LOG.debug("username for accounting service : {}", username);
		String res = accountingService.getPasswordHash(username);
		if (res == null) {
			throw new NotFoundException(String.format("Account username: %s not found",
					username));
		}
		return res;
	}
	@GetMapping(ACCOUNTS_TIME_USERNAME)
	LocalDateTime getActivationTime(@PathVariable String username) {
		LocalDateTime res = accountingService.getActivationTime(username);
		if (res == null) {
			throw new NotFoundException(String.format("Account username: %s not found",
					username));
		}
		return res;
	}
	@GetMapping(ACCOUNTS_ROLE_USERNAME)
	String getRole(@PathVariable  String username) {
		String res = accountingService.getRole(username);
		if (res == null) {
			throw new NotFoundException(String.format("Account username: %s not found",
					username));
		}
		return res;
	}
}
