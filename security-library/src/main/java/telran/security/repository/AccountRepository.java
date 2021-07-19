package telran.security.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import telran.secirity.entities.AccountEntity;

public interface AccountRepository extends MongoRepository<AccountEntity, String> {

}
