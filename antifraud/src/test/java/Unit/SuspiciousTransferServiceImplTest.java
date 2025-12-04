package Unit;

import config.TestConstants;
import dto.SuspiciousCardTransferDto;
import jakarta.persistence.EntityNotFoundException;
import mappers.SuspiciousTransferMapper;
import model.SuspiciousCardTransfer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.SuspiciousCardTransferRepository;
import service.SuspiciousTransferServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SuspiciousTransferServiceImplTest {

    @Mock private SuspiciousCardTransferRepository cardRepo;
    @Mock private SuspiciousTransferMapper mapper;

    @InjectMocks
    private SuspiciousTransferServiceImpl service;

    @Test
    @DisplayName("createCard — успешное создание")
    void createCard_success() {
        SuspiciousCardTransferDto dto = cardDto();
        SuspiciousCardTransfer entity = cardEntity();
        SuspiciousCardTransfer saved = cardEntity();
        saved.setId(TestConstants.TEST_ID);

        when(mapper.toCardEntity(dto)).thenReturn(entity);
        when(cardRepo.save(entity)).thenReturn(saved);
        when(mapper.toCardDto(saved)).thenReturn(dto.toBuilder().id(TestConstants.TEST_ID).build());

        SuspiciousCardTransferDto result = service.createCard(dto);

        assertNotNull(result.getId());
        verify(cardRepo).save(entity);
    }

    @Test
    @DisplayName("updateCard — несуществующий ID → EntityNotFoundException")
    void updateCard_notFound_throwsException() {
        SuspiciousCardTransferDto dto = cardDto();
        when(cardRepo.findById(TestConstants.NON_EXISTENT_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                service.updateCard(TestConstants.NON_EXISTENT_ID, dto));

        verify(cardRepo, never()).save(any());
    }

    private SuspiciousCardTransferDto cardDto() {
        return SuspiciousCardTransferDto.builder()
                .blocked(TestConstants.BLOCKED_FALSE)
                .suspicious(TestConstants.SUSPICIOUS_TRUE)
                .blockedReason(TestConstants.BLOCKED_REASON)
                .suspiciousReason(TestConstants.SUSPICIOUS_REASON)
                .build();
    }

    private SuspiciousCardTransfer cardEntity() {
        return SuspiciousCardTransfer.builder()
                .blocked(TestConstants.BLOCKED_FALSE)
                .suspicious(TestConstants.SUSPICIOUS_TRUE)
                .blockedReason(TestConstants.BLOCKED_REASON)
                .suspiciousReason(TestConstants.SUSPICIOUS_REASON)
                .build();
    }
}