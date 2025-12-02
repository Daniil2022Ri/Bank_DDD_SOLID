package service;


public interface AuditService {

    void logCreate(String entityType, Object newEntity, String username);
    void logUpdate(String entityType, Object oldEntity, Object newEntity, String username);
    void logCreateOperation(String entityType, Object dto, Object result, String username);
    void logUpdateOperation(String entityType, Long id, Object oldEntity, Object newEntity, String username);
}
