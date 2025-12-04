package AOP;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

import static config.ApplicationConstant.BY_ID_TARGET;
import static config.ApplicationConstant.ENTITY_NAME_GET_ID;
import static config.ApplicationConstant.ERR_CRITICAL_MSG;
import static config.ApplicationConstant.ERR_MSG_INP_DETAIL;
import static config.ApplicationConstant.EXCEPTION_NAME;
import static config.ApplicationConstant.GET_NAME_TARGET;
import static config.ApplicationConstant.METHOD_TARGET_CREATE;
import static config.ApplicationConstant.METHOD_TARGET_UPDATE;
import static config.ApplicationConstant.MSG_BLOCKED;
import static config.ApplicationConstant.MSG_BLOCKED_REASON;
import static config.ApplicationConstant.MSG_CREATED;
import static config.ApplicationConstant.MSG_DELETED;
import static config.ApplicationConstant.MSG_ERR_ASPECT_CREATE;
import static config.ApplicationConstant.MSG_ERR_ASPECT_DELETE;
import static config.ApplicationConstant.MSG_ERR_ASPECT_GET;
import static config.ApplicationConstant.MSG_ERR_ASPECT_UPDATE;
import static config.ApplicationConstant.MSG_ERR_SERVICE;
import static config.ApplicationConstant.MSG_NOT_DATE;
import static config.ApplicationConstant.MSG_RECEIVED;
import static config.ApplicationConstant.MSG_SUSPICIOUS;
import static config.ApplicationConstant.MSG_UPDATED;
import static config.ApplicationConstant.NAME_METHOD_GET_BLOCKED_REASON;
import static config.ApplicationConstant.NAME_METHOD_GET_SUSPICIOUS_REASON;
import static config.ApplicationConstant.NAME_METHOD_IS_BLOCKED;
import static config.ApplicationConstant.NAME_METHOD_IS_SUSPICIOUS;
import static config.ApplicationConstant.NAME_RETURNING;
import static config.ApplicationConstant.POINTCUT_CREATE;
import static config.ApplicationConstant.POINTCUT_DELETE;
import static config.ApplicationConstant.POINTCUT_GET_BY_ID;
import static config.ApplicationConstant.POINTCUT_SERVICE;
import static config.ApplicationConstant.POINTCUT_UPDATE;
import static config.ApplicationConstant.RETURNING_RES;
import static config.ApplicationConstant.UNKNOWN_NUM;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class AuditAspect {

    @AfterReturning(pointcut = POINTCUT_CREATE, returning = RETURNING_RES)
    public void logCreateOperation(JoinPoint joinPoint, Object result) {
        try {
            String methodName = joinPoint.getSignature().getName();
            String entityType = methodName.replace(METHOD_TARGET_CREATE, "");
            log.info(MSG_CREATED, entityType, methodName, extractSuspiciousDetails(result));
        } catch (Exception e) {
            log.error(MSG_ERR_ASPECT_CREATE, joinPoint.getSignature().getName(), e);
        }
    }

    @AfterReturning(pointcut = POINTCUT_UPDATE, returning = RETURNING_RES)
    public void logUpdateOperation(JoinPoint joinPoint, Object result) {
        try {
            String methodName = joinPoint.getSignature().getName();
            String entityType = methodName.replace(METHOD_TARGET_UPDATE, "");
            Long id = extractId(result);
            log.warn(MSG_UPDATED, entityType, id, extractSuspiciousDetails(result));
        } catch (Exception e) {
            log.error(MSG_ERR_ASPECT_UPDATE, joinPoint.getSignature().getName(), e);
        }
    }

    @Before(POINTCUT_DELETE)
    public void logDeleteOperation(JoinPoint joinPoint) {
        try {
            Object[] args = joinPoint.getArgs();
            Long id = (args.length > 0) ? (Long) args[0] : null;
            String type = (args.length > 1) ? (String) args[1] : UNKNOWN_NUM;
            log.info(MSG_DELETED, type, id);
        } catch (Exception e) {
            log.error(MSG_ERR_ASPECT_DELETE, joinPoint.getSignature().getName(), e);
        }
    }

    @AfterThrowing(pointcut = POINTCUT_SERVICE, throwing = EXCEPTION_NAME)
    public void logExceptions(JoinPoint joinPoint, Exception ex) {
        try {
            String methodName = joinPoint.getSignature().getName();
            log.error(MSG_ERR_SERVICE, methodName, ex.getMessage());
        } catch (Exception e) {
            log.error(ERR_CRITICAL_MSG, e);
        }
    }

    @AfterReturning(pointcut = POINTCUT_GET_BY_ID, returning = NAME_RETURNING)
    public void logGetByIdOperations(JoinPoint joinPoint, Object result) {
        try {
            String methodName = joinPoint.getSignature().getName();
            Object[] args = joinPoint.getArgs();
            Long id = (args.length > 0) ? (Long) args[0] : null;
            String entityType = methodName.replace(GET_NAME_TARGET, "").replace(BY_ID_TARGET, "");
            log.debug(MSG_RECEIVED, entityType, id);
        } catch (Exception e) {
            log.error(MSG_ERR_ASPECT_GET, joinPoint.getSignature().getName(), e);
        }
    }
    private Long extractId(Object dto) {
        if (dto == null) return null;
        try {
            Method getIdMethod = dto.getClass().getMethod(ENTITY_NAME_GET_ID);
            return (Long) getIdMethod.invoke(dto);
        } catch (Exception e) {
            return null;
        }
    }
    private String extractSuspiciousDetails(Object dto) {
        if (dto == null) return MSG_NOT_DATE;
        try {
            Class<?> dtoClass = dto.getClass();
            Boolean isBlocked = (Boolean) invokeSafe(dtoClass, dto, NAME_METHOD_IS_BLOCKED);
            Boolean isSuspicious = (Boolean) invokeSafe(dtoClass, dto, NAME_METHOD_IS_SUSPICIOUS);
            String blockedReason = (String) invokeSafe(dtoClass, dto, NAME_METHOD_GET_BLOCKED_REASON);
            String suspiciousReason = (String) invokeSafe(dtoClass, dto, NAME_METHOD_GET_SUSPICIOUS_REASON);
            return String.format(MSG_BLOCKED + isBlocked, MSG_SUSPICIOUS + isSuspicious,
                    MSG_BLOCKED_REASON + blockedReason, " " + suspiciousReason);
        } catch (Exception e) {
            return ERR_MSG_INP_DETAIL + e.getMessage();
        }
    }

    private Object invokeSafe(Class<?> clazz, Object obj, String methodName) {
        try {
            return clazz.getMethod(methodName).invoke(obj);
        } catch (Exception e) {
            return null;
        }
    }
}