
package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.Audit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.AuditRepository;



import java.time.LocalTime;


@Service
@Slf4j
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    @Autowired
    private final AuditRepository auditRepository;
    @Autowired
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void logCreate(String entityType, Object newEntity, String username) {
        Audit audit = new Audit();
        audit.setEntityType(entityType);
        audit.setOperationType("CREATE");
        audit.setCreatedBy(username);
        audit.setCreatedAt(LocalTime.now());
        audit.setNewEntityJson(toJson(newEntity));
        auditRepository.save(audit);
    }

    @Override
    public void logUpdate(String entityType, Object oldEntity, Object newEntity, String username) {
        Audit audit = new Audit();
        audit.setEntityType(entityType);
        audit.setOperationType("UPDATE");
        audit.setModifiedBy(username);
        audit.setModifiedAt(LocalTime.now());
        audit.setEntityJson(toJson(oldEntity));
        audit.setNewEntityJson(toJson(newEntity));
        auditRepository.save(audit);
    }

    @Override
    public void logCreateOperation(String entityType, Object dto, Object result, String username) {
        try {
            Audit audit = Audit.builder()
                    .entityType(entityType)
                    .operationType("CREATE")
                    .createdBy(username)
                    .createdAt(LocalTime.now())
                    .newEntityJson(toJson(result))
                    .build();

            auditRepository.save(audit);
            log.info("Аудит: Создана {} пользователем {}", entityType, username);

        } catch (Exception e) {
            log.error("Ошибка при логировании создания {}: {}", entityType, e.getMessage());
            throw new RuntimeException("Audit logging failed", e);
        }
    }

    @Override
    public void logUpdateOperation(String entityType, Long id, Object oldEntity, Object newEntity, String username) {
        try {
            Audit audit = Audit.builder()
                    .entityType(entityType)
                    .operationType("UPDATE")
                    .modifiedBy(username)
                    .modifiedAt(LocalTime.now())
                    .entityJson(toJson(oldEntity))
                    .newEntityJson(toJson(newEntity))
                    .build();

            auditRepository.save(audit);
            log.info("Аудит: Обновлена {} ID {} пользователем {}", entityType, id, username);

        } catch (Exception e) {
            log.error("Ошибка при логировании обновления {} ID {}: {}", entityType, id, e.getMessage());
            throw new RuntimeException("Audit logging failed", e);
        }
    }

    private String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.warn("Ошибка сериализации объекта в JSON: {}", e.getMessage());
            return "{\"error\": \"serialization_failed\"}";
        }
    }
}
