package Unit;

import AOP.TestableAuditAspect;
import config.ApplicationConstant;
import config.TestConstants;
import dto.SuspiciousCardTransferDto;
import mappers.SuspiciousTransferMapper;
import model.SuspiciousCardTransfer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import repository.SuspiciousAccountTransferRepository;
import repository.SuspiciousCardTransferRepository;
import repository.SuspiciousPhoneTransferRepository;
import service.SuspiciousTransferService;
import service.SuspiciousTransferServiceImpl;

import java.util.Optional;

import static config.ApplicationConstant.MSG_CREATED;
import static config.ApplicationConstant.MSG_UPDATED;
import static config.AuditLogExpectations.TYPE_CARD;
import static config.ApplicationConstant.MSG_DELETED;
import static config.ApplicationConstant.MSG_ERR_SERVICE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuditAspectTest {

    @Spy
    private TestableAuditAspect auditAspect;

    @Mock private SuspiciousCardTransferRepository cardRepo;
    @Mock private SuspiciousPhoneTransferRepository phoneRepo;
    @Mock private SuspiciousAccountTransferRepository accountRepo;
    @Mock private SuspiciousTransferMapper mapper;
    private SuspiciousTransferService proxyService;

    @BeforeEach
    void setUp() {
        SuspiciousTransferServiceImpl realService = new SuspiciousTransferServiceImpl(
                cardRepo, phoneRepo, accountRepo, mapper
        );
        AspectJProxyFactory factory = new AspectJProxyFactory(realService);
        factory.addAspect(auditAspect);
        proxyService = factory.getProxy();
    }

    @Test
    @DisplayName("Логирование при создании карты (create)")
    void logCreateOperation() {
        SuspiciousCardTransferDto dto = getTestDto();
        SuspiciousCardTransfer entity = new SuspiciousCardTransfer();
        when(mapper.toCardEntity(any())).thenReturn(entity);
        when(cardRepo.save(any())).thenReturn(entity);
        when(mapper.toCardDto(any())).thenReturn(dto);
        proxyService.createCard(dto);
        verify(auditAspect).info(
                eq(ApplicationConstant.MSG_CREATED),
                eq(TestConstants.ASPECT_TYPE_CARD),
                eq(TestConstants.ASPECT_METHOD_CREATE_CARD),
                argThat(s -> s.toString().contains(TestConstants.LOG_PART_BLOCKED_TRUE))
        );
    }

    @Test
    @DisplayName("Логирование при обновлении (update)")
    void logUpdateOperation() {
        SuspiciousCardTransferDto dto = getTestDto();
        SuspiciousCardTransfer entity = new SuspiciousCardTransfer();
        when(cardRepo.findById(anyLong())).thenReturn(Optional.of(entity));
        when(cardRepo.save(any())).thenReturn(entity);
        when(mapper.toCardDto(any())).thenReturn(dto);
        proxyService.updateCard(TestConstants.ID_VALID, dto);
        verify(auditAspect).warn(
                eq(ApplicationConstant.MSG_UPDATED),
                eq(TestConstants.ASPECT_TYPE_CARD),
                eq(TestConstants.ID_VALID),
                argThat(s -> s.toString().contains(TestConstants.LOG_PART_SUSPICIOUS_TRUE))
        );
    }

    @Test
    @DisplayName("Логирование при удалении (delete)")
    void logDeleteOperation() {
        doNothing().when(cardRepo).deleteById(anyLong());

        proxyService.deleteSuspiciousTransfer(TestConstants.ID_VALID, ApplicationConstant.TYPE_CARD);

        verify(auditAspect).info(
                eq(ApplicationConstant.MSG_DELETED),
                eq(ApplicationConstant.TYPE_CARD),
                eq(TestConstants.ID_VALID)
        );
    }

    @Test
    @DisplayName("Логирование исключений сервиса")
    void logExceptions() {
        RuntimeException ex = new RuntimeException("Database Error");
        when(cardRepo.findById(anyLong())).thenThrow(ex);
        try {
            proxyService.getCardById(TestConstants.ID_VALID);
        } catch (Exception ignored) {}
        verify(auditAspect).error(
                eq(ApplicationConstant.MSG_ERR_SERVICE),
                contains("getCardById"),
                eq(ex.getMessage())
        );
    }

    private SuspiciousCardTransferDto getTestDto() {
        return SuspiciousCardTransferDto.builder()
                .id(TestConstants.ID_VALID)
                .blocked(TestConstants.BLOCKED_TRUE)
                .suspicious(TestConstants.SUSPICIOUS_TRUE)
                .blockedReason(TestConstants.REASON_BLOCKED)
                .suspiciousReason(TestConstants.REASON_SUSPICIOUS)
                .build();
    }
}