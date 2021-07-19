package telran.secirity.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import static telran.security.constants.MongoConstants.*;

import java.time.LocalDateTime;

@Document (collection = ACCOUNTS_COLLECTION_NAME)
public class AccountEntity {
@Id
	String username;
String passwordHash;

LocalDateTime activationTime;
String role;
public AccountEntity(String username, String passwordHash, LocalDateTime activationTime, String role) {
	super();
	this.username = username;
	this.passwordHash = passwordHash;
	this.activationTime = activationTime;
	this.role = role;
}
public String getPasswordHash() {
	return passwordHash;
}
public void setPasswordHash(String passwordHash) {
	this.passwordHash = passwordHash;
}
public LocalDateTime getActivationTime() {
	return activationTime;
}
public void setActivationTime(LocalDateTime activationTime) {
	this.activationTime = activationTime;
}
public String getRole() {
	return role;
}
public void setRole(String role) {
	this.role = role;
}
public String getUsername() {
	return username;
}
}
