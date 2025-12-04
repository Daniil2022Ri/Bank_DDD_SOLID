package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.AuditDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.Audit;
import org.springframework.stereotype.Service;
import repository.AuditRepository;

import java.time.LocalDateTime;
import static config.ApplicationConstant.OPERATION_CREATE;
import static config.ApplicationConstant.OPERATION_UPDATE;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final AuditRepository auditRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void logCreate(String entityType, Object newEntity, String username) {
        saveAudit(entityType, OPERATION_CREATE, username, null, toJson(newEntity), null);
    }

    @Override
    public void logUpdate(String entityType, Object oldEntity, Object newEntity, String username) {
        saveAudit(entityType, OPERATION_UPDATE, username, toJson(oldEntity), toJson(newEntity), null);
    }

    @Override
    public void logCreateOperation(String entityType, Object dto, Object result, String username) {
        saveAudit(entityType, OPERATION_CREATE, username, null, toJson(result), null);
    }

    @Override
    public void logUpdateOperation(String entityType, Long id, Object oldEntity, Object newEntity, String username) {
        saveAudit(entityType, OPERATION_UPDATE, username, toJson(oldEntity), toJson(newEntity), null);
    }

    @Override
    public void logOperation(AuditDto context) {
        if (context == null) return;

        try {
            Audit audit = Audit.builder()
                    .entityType(context.getEntityType())
                    .operationType(context.getOperationType())
                    .createdBy(context.getCreatedBy())
                    .modifiedBy(context.getModifiedBy())
                    .createdAt(context.getCreatedAt())
                    .modifiedAt(context.getModifiedAt())
                    .newEntityJson(context.getNewEntityJson())
                    .entityJson(context.getEntityJson())
                    .build();
            auditRepository.save(audit);
            log.info("Ручная запись аудита сохранена: {}", context.getEntityType());
        } catch (Exception e) {
            log.error("Ошибка при сохранении ручного аудита: {}", e.getMessage());
        }
    }

    private void saveAudit(String entityType, String opType, String username, String oldJson, String newJson, String details) {
        try {
            Audit audit = Audit.builder()
                    .entityType(entityType)
                    .operationType(opType)
                    .createdBy(username)
                    .modifiedBy(username)
                    .createdAt(LocalDateTime.now())
                    .modifiedAt(LocalDateTime.now())
                    .entityJson(oldJson)
                    .newEntityJson(newJson)
                    .build();
            auditRepository.save(audit);
        } catch (Exception e) {
            log.error("Ошибка сохранения аудита: {}", e.getMessage());
        }
    }

    private String toJson(Object obj) {
        if (obj == null) return null;
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.warn("Ошибка сериализации: {}", e.getMessage());
            return "{\"error\": \"serialization_failed\"}";
        }
    }
}