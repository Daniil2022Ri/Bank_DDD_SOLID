package Unit;

import AOP.TestableAuditAspect;
import config.TestConstants;
import dto.SuspiciousAccountTransferDto;
import dto.SuspiciousCardTransferDto;
import dto.SuspiciousPhoneTransferDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import service.SuspiciousTransferService;
import service.SuspiciousTransferServiceImpl;

import static config.ApplicationConstant.*;
import static config.AuditLogExpectations.ERROR_MESSAGE_DB;
import static config.AuditLogExpectations.LOG_BLOCKED_FALSE;
import static config.AuditLogExpectations.LOG_BLOCKED_REASON;
import static config.AuditLogExpectations.LOG_BLOCKED_TRUE;
import static config.AuditLogExpectations.LOG_ENTITY_ACCOUNT;
import static config.AuditLogExpectations.LOG_ENTITY_CARD;
import static config.AuditLogExpectations.LOG_ENTITY_PHONE;
import static config.AuditLogExpectations.LOG_SUSPICIOUS_REASON;
import static config.AuditLogExpectations.LOG_SUSPICIOUS_TRUE;
import static config.AuditLogExpectations.METHOD_CREATE_ACCOUNT;
import static config.AuditLogExpectations.METHOD_CREATE_CARD;
import static config.AuditLogExpectations.METHOD_CREATE_PHONE;
import static config.AuditLogExpectations.TEST_ENTITY_ID;
import static config.AuditLogExpectations.TEST_GET_ID;
import static config.AuditLogExpectations.TEST_UPDATE_ID;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Тесты AuditAspect — полное покрытие без хардкода")
class AuditAspectTest {

    @Spy
    private TestableAuditAspect auditAspect;

    @Mock
    private SuspiciousTransferServiceImpl realService;

    private SuspiciousTransferService proxy;

    @BeforeEach
    void setUp() {
        AspectJProxyFactory factory = new AspectJProxyFactory(realService);
        factory.addAspect(auditAspect);
        proxy = factory.getProxy();
    }

    @Test
    @DisplayName("createCard → логируется создание карты")
    void logCreateOperation_card_success() {
        when(realService.createCard(any())).thenReturn(cardDto());
        proxy.createCard(cardDto());

        verify(auditAspect).info(
                eq(MSG_CREATED),
                eq("Card"),
                eq("createCard"),
                contains(LOG_BLOCKED_TRUE),
                contains(LOG_SUSPICIOUS_TRUE),
                contains(LOG_BLOCKED_REASON()),
                contains(LOG_SUSPICIOUS_REASON())
        );
    }

    @Test
    @DisplayName("createPhone → логируется создание телефона")
    void logCreateOperation_phone_success() {
        when(realService.createPhone(any())).thenReturn(phoneDto());
        proxy.createPhone(phoneDto());

        verify(auditAspect).info(
                eq(MSG_CREATED),
                eq(LOG_ENTITY_PHONE),
                eq(METHOD_CREATE_PHONE),
                contains(LOG_BLOCKED_FALSE),
                contains(LOG_SUSPICIOUS_TRUE)
        );
    }

    @Test
    @DisplayName("updateAccount → логируется обновление")
    void logUpdateOperation_account_success() {
        var dto = accountDto().toBuilder().id(TEST_UPDATE_ID).build();
        when(realService.updateAccount(eq(TEST_UPDATE_ID), any())).thenReturn(dto);
        proxy.updateAccount(TEST_UPDATE_ID, dto);

        verify(auditAspect).warn(
                eq(MSG_UPDATED),
                eq(LOG_ENTITY_ACCOUNT),
                eq(TEST_UPDATE_ID),
                contains(LOG_BLOCKED_TRUE),
                contains(LOG_SUSPICIOUS_REASON())
        );
    }

    @Test
    @DisplayName("delete → логируется удаление")
    void logDeleteOperation_success() {
        proxy.deleteSuspiciousTransfer(TEST_ENTITY_ID, TYPE_CARD);

        verify(auditAspect).info(MSG_DELETED, TYPE_CARD, TEST_ENTITY_ID);
    }

    @Test
    @DisplayName("getById → логируется получение")
    void logGetByIdOperations_success() {
        when(realService.getCardById(TEST_GET_ID)).thenReturn(cardDto());
        proxy.getCardById(TEST_GET_ID);

        verify(auditAspect).debug(MSG_RECEIVED, LOG_ENTITY_CARD, TEST_GET_ID);
    }

    @Test
    @DisplayName("Исключение → логируется ошибка сервиса")
    void logExceptions_onException() {
        doThrow(new RuntimeException(ERROR_MESSAGE_DB))
                .when(realService).createAccount(any());

        assertThrows(RuntimeException.class, () -> proxy.createAccount(accountDto()));

        verify(auditAspect).error(
                eq(MSG_ERR_SERVICE),
                contains(METHOD_CREATE_ACCOUNT),
                contains(ERROR_MESSAGE_DB)
        );
    }

    private SuspiciousCardTransferDto cardDto() {
        return SuspiciousCardTransferDto.builder()
                .id(TestConstants.TEST_ID)
                .blocked(TestConstants.BLOCKED_TRUE)
                .suspicious(TestConstants.SUSPICIOUS_TRUE)
                .blockedReason(TestConstants.BLOCKED_REASON)
                .suspiciousReason(TestConstants.SUSPICIOUS_REASON)
                .build();
    }

    private SuspiciousPhoneTransferDto phoneDto() {
        return SuspiciousPhoneTransferDto.builder()
                .id(TestConstants.TEST_ID)
                .blocked(TestConstants.BLOCKED_FALSE)
                .suspicious(TestConstants.SUSPICIOUS_TRUE)
                .blockedReason(null)
                .suspiciousReason(TestConstants.SUSPICIOUS_REASON)
                .build();
    }

    private SuspiciousAccountTransferDto accountDto() {
        return SuspiciousAccountTransferDto.builder()
                .id(TestConstants.TEST_ID)
                .blocked(TestConstants.BLOCKED_TRUE)
                .suspicious(TestConstants.SUSPICIOUS_TRUE)
                .blockedReason(TestConstants.BLOCKED_REASON)
                .suspiciousReason(TestConstants.SUSPICIOUS_REASON)
                .build();
    }
}