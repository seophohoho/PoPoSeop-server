package poposeop.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import poposeop.account.entity.InGameEntity;

public interface InGameRepository extends JpaRepository<InGameEntity, String> {
    Boolean existsByUsername(String Username);
    InGameEntity findByUsername(String username);
}
