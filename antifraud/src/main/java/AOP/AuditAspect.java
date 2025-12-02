package AOP;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import service.AuditService;



@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class AuditAspect {

    private final AuditService auditService;

    @AfterReturning(
            pointcut = "execution(* service.SuspiciousTransferServiceImpl.create*(..)) && args(dto)",
            returning = "result"
    )
    public void logCreateOperation(JoinPoint joinPoint, Object dto, Object result) {
        try {
            String operationType = "CREATE";
            String entityType = extractEntityType(joinPoint);
            String username = "Заглушка";//getCurrentUsername();

            log.debug("AOP: Логирование создания {} пользователем {}", entityType, username);
            auditService.logCreateOperation(entityType, dto, result, username);

        } catch (Exception e) {
            log.error("AOP ошибка при логировании создания: {}", e.getMessage(), e);
        }
    }

    @AfterReturning(
            pointcut = "execution(* service.SuspiciousTransferServiceImpl.update*(..)) && args(id, dto)",
            returning = "result"
    )
    public void logUpdateOperation(JoinPoint joinPoint, Long id, Object dto, Object result) {
        try {
            String operationType = "UPDATE";
            String entityType = extractEntityType(joinPoint);
             String username = "Заглушка";//getCurrentUsername();
            log.debug("AOP: Логирование обновления {} ID {} пользователем {}", entityType, id, username);
            auditService.logUpdateOperation(entityType, id, dto, result, username);

        } catch (Exception e) {
            log.error("AOP ошибка при логировании обновления: {}", e.getMessage(), e);
        }
    }

    private String extractEntityType(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();

        if (methodName.contains("Card")) return "SUSPICIOUS_CARD_TRANSFER";
        if (methodName.contains("Phone")) return "SUSPICIOUS_PHONE_TRANSFER";
        if (methodName.contains("Account")) return "SUSPICIOUS_ACCOUNT_TRANSFER";

        return "UNKNOWN";
    }

    /* В процессе...
    private String getCurrentUsername() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getName)
                .orElse("system");
    }
    */
}