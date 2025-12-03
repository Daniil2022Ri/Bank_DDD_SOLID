package AOP;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import service.AuditService;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class AuditAspect {
    private final AuditService auditService;

    @AfterReturning(pointcut = "execution(* service.AccountServiceImpl.createAccount(..))", returning = "result")
    public void logCreateOperation(JoinPoint joinPoint, Object result) {
        try {
            Object[] args = joinPoint.getArgs();
            if (args.length >= 2) {
                String createdBy = (String) args[1];
                log.info("AOP: Создание учетной записи: {}", createdBy);
            }
        } catch (Exception e) {
            log.error("AOP Ошибка в создании операции Создания: {}", e.getMessage());
        }
    }

    @AfterReturning(pointcut = "execution(* service.AccountServiceImpl.updateAccount(..))", returning = "result")
    public void logUpdateOperation(JoinPoint joinPoint, Object result) {
        try {
            Object[] args = joinPoint.getArgs();
            if (args.length >= 3) {
                String modifiedBy = (String) args[2];
                log.info("AOP: Обновление аккаунта завершено: {}", modifiedBy);
            }
        } catch (Exception e) {
            log.error("AOP Ошибка в создании операции Обновления: {}", e.getMessage());
        }
    }

    @Before("execution(* service.AccountServiceImpl.deleteAccount(..))")
    public void logDeleteOperation(JoinPoint joinPoint) {
        try {
            Object[] args = joinPoint.getArgs();
            if (args.length >= 2) {
                String deletedBy = (String) args[1];
                log.info("AOP: Удаление учетной записи инициализированно: {}", deletedBy);
            }
        } catch (Exception e) {
            log.error("AOP ошибка в в создании операции Удаления: {}", e.getMessage());
        }
    }
}
