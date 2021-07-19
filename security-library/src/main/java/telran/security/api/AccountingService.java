package telran.security.api;

import java.time.LocalDateTime;

import telran.security.dto.AccountDto;

public interface AccountingService {
boolean addAccount(AccountDto accountDto);
boolean removeAccount(String username);
boolean updatePassword(String username, String password);
String getPasswordHash(String username);
LocalDateTime getActivationTime(String username);
String getRole(String username);


}
