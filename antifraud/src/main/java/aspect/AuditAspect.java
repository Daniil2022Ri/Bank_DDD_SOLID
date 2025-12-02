package aspect;

import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import service.AuditService;

import java.util.Optional;

@Aspect
@Component
@AllArgsConstructor
public class AuditAspect {

    private final AuditService auditService;

    @AfterReturning(pointcut = "execution(* com.bank.antifraud.service.SuspiciousTransferServiceImpl.create*(..)) && args(dto)",
            returning = "result")
    public void logCreate(JoinPoint jp, Object dto, Object result) {
        String type = extractType(jp);
        String username = getCurrentUsername();
        auditService.logCreate(type, result, username);
    }

    @AfterReturning(pointcut = "execution(* com.bank.antifraud.service.SuspiciousTransferServiceImpl.update*(..)) && args(id, dto)",
            returning = "result")
    public void logUpdate(JoinPoint jp, Long id, Object dto, Object result) {
        String type = extractType(jp);
        String username = getCurrentUsername();
        // oldEntity можно получить из БД, но для простоты — пропустим
        auditService.logUpdate(type, "{}", result, username);
    }

    private String extractType(JoinPoint jp) {
        String method = jp.getSignature().getName();
        if (method.contains("Card")) return "SuspiciousCardTransfer";
        if (method.contains("Phone")) return "SuspiciousPhoneTransfer";
        if (method.contains("Account")) return "SuspiciousAccountTransfer";
        return "Unknown";
    }

    private String getCurrentUsername() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(auth -> auth.getName())
                .orElse("system");
    }
}
