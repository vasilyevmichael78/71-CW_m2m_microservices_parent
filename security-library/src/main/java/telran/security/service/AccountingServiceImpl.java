package telran.security.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import telran.secirity.entities.AccountEntity;
import telran.security.api.AccountingService;
import telran.security.dto.AccountDto;
import telran.security.repository.AccountRepository;

@Service
public class AccountingServiceImpl implements AccountingService {
	static final Logger LOG = LoggerFactory.getLogger(AccountingService.class); 
@Autowired
AccountRepository accountRepository;
@Autowired
PasswordEncoder passwordEncoder;
	@Override
	public boolean addAccount(AccountDto accountDto) {
		if(accountRepository.existsById(accountDto.username)) {
			return false;
		}
		String passwordHash = passwordEncoder.encode(accountDto.password);
		AccountEntity account =
				new AccountEntity(accountDto.username,
						passwordHash, LocalDateTime.now(), accountDto.role);
		accountRepository.save(account);
		return true;
	}

	@Override
	public boolean removeAccount(String username) {
		if(accountRepository.existsById(username)) {
			return false;
		}
		accountRepository.deleteById(username);
		return true;
	}

	@Override
	public boolean updatePassword(String username, String password) {
		AccountEntity account =
				accountRepository.findById(username).orElse(null);
		if (account == null) {
			return false;
		}
		account.setPasswordHash(passwordEncoder.encode(password));
		account.setActivationTime(LocalDateTime.now());
		accountRepository.save(account);
		return true;
	}

	@Override
	public String getPasswordHash(String username) {
		AccountEntity account =
				accountRepository.findById(username).orElse(null);
		
		return account != null ? account.getPasswordHash() : null;
	}

	@Override
	public LocalDateTime getActivationTime(String username) {
		AccountEntity account =
				accountRepository.findById(username).orElse(null);
		return account != null ? account.getActivationTime() : null;
	}

	@Override
	public String getRole(String username) {
		AccountEntity account =
				accountRepository.findById(username).orElse(null);
		return account != null ? account.getRole() : null;
	}

}
