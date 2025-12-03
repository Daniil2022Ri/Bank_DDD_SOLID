package repository;

import model.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {

    // Поиск по ID аудита конкретного микросервиса
    Optional<History> findByAccountAuditId(Long accountAuditId);
    Optional<History> findByAuthorizationAuditId(Long authorizationAuditId);
    Optional<History> findByAntiFraudAuditId(Long antiFraudAuditId);
    Optional<History> findByProfileAuditId(Long profileAuditId);
    Optional<History> findByTransferAuditId(Long transferAuditId);
    Optional<History> findByPublicBankInfoAuditId(Long publicBankInfoAuditId);

    // Поиск всех записей, где есть данные от конкретного микросервиса
    List<History> findByAccountAuditIdIsNotNull();
    List<History> findByAuthorizationAuditIdIsNotNull();
    List<History> findByAntiFraudAuditIdIsNotNull();
    List<History> findByProfileAuditIdIsNotNull();
    List<History> findByTransferAuditIdIsNotNull();
    List<History> findByPublicBankInfoAuditIdIsNotNull();

    // Поиск по диапазону дат
    List<History> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    // Агрегированный запрос для получения всех записей с сортировкой
    @Query("SELECT h FROM History h ORDER BY h.createdAt DESC")
    List<History> findAllOrderByCreatedAtDesc();
}
