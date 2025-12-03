package repositories;

import model.Audit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import service.AuditStatus;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Long> {
    List<Audit> findByPasswordId(Long passwordId);
    List<Audit> findByAccountNumber(Long accountNumber);
    List<Audit> findByProfileId(Long profileId);
    List<Audit> findByNegativeBalance(boolean negativeBalance);
    List<Audit> findByBankDetailsId(Long bankDetailsId);
}
