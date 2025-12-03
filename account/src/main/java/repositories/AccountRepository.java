package repositories;

import model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByEntityType(String entityType);
    List<Account> findByCreatedBy(String createdBy);
    List<Account> findByOperationType(String operationType);
    List<Account> findByEntityJsonContaining(String searchText);
    List<Account> findByNewEntityJsonContaining(String searchText);
}
