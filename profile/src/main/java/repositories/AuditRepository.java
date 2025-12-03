package repositories;

import dto.AuditDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AuditRepository extends JpaRepository<AuditDto , Long> {
    Optional<AuditDto> findByEntityTypeAndOperationType(String entityType, String operationType);
    Optional<AuditDto> findByCreateAT(LocalDateTime time);
    Optional<AuditDto> findByModifiedAt(LocalDateTime time);
}
