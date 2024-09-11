package poposeop.account.repository;

import org.springframework.data.repository.CrudRepository;
import poposeop.account.entity.AccountEntity;

public interface AccountRepository extends CrudRepository<AccountEntity, String> {
    Boolean existsByUsername(String Username);
}
