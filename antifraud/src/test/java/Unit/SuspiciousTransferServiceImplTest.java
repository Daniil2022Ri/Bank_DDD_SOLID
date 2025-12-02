package Unit;

import dto.SuspiciousAccountTransferDto;
import dto.SuspiciousCardTransferDto;
import dto.SuspiciousPhoneTransferDto;
import jakarta.persistence.EntityNotFoundException;
import mapers.SuspiciousTransferMapper;
import model.SuspiciousAccountTransfer;
import model.SuspiciousCardTransfer;
import model.SuspiciousPhoneTransfer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.SuspiciousAccountTransferRepository;
import repository.SuspiciousCardTransferRepository;
import repository.SuspiciousPhoneTransferRepository;
import service.SuspiciousTransferServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SuspiciousTransferServiceImplTest {

    @Mock
    private SuspiciousCardTransferRepository cardRepo;

    @Mock
    private SuspiciousPhoneTransferRepository phoneRepo;

    @Mock
    private SuspiciousAccountTransferRepository accountRepo;

    @Mock
    private SuspiciousTransferMapper mapperService;

    @InjectMocks
    private SuspiciousTransferServiceImpl service;

    private final Long TEST_ID = 1L;
    private final boolean BLOCKED_TRUE = true;
    private final boolean SUSPICIOUS_TRUE = true;
    private final String SUSPICIOUS_REASON = "Подозрительные действия";
    private final String BLOCKED_REASON = "Причина блокировки";


    @Test
    void createCard_WithValidDto_ShouldReturnSavedDto() {

        SuspiciousCardTransferDto inputDto = createTestCardDto();
        SuspiciousCardTransfer entity = createTestCardEntity();
        SuspiciousCardTransfer savedEntity = createTestCardEntity();
        SuspiciousCardTransferDto expectedDto = createTestCardDto();

        when(mapperService.toCardEntity(inputDto)).thenReturn(entity);
        when(cardRepo.save(entity)).thenReturn(savedEntity);
        when(mapperService.toCardDto(savedEntity)).thenReturn(expectedDto);

        SuspiciousCardTransferDto result = service.createCard(inputDto);

        assertNotNull(result);
        assertEquals(expectedDto.getId(), result.getId());

        verify(mapperService).toCardEntity(inputDto);
        verify(cardRepo).save(entity);
        verify(mapperService).toCardDto(savedEntity);
    }

    @Test
    void createCard_WhenSaveFails_ShouldThrowException() {
        SuspiciousCardTransferDto inputDto = createTestCardDto();
        SuspiciousCardTransfer entity = createTestCardEntity();

        when(mapperService.toCardEntity(inputDto)).thenReturn(entity);
        when(cardRepo.save(entity)).thenThrow(new RuntimeException("DB Error"));

        assertThrows(RuntimeException.class, () -> service.createCard(inputDto));

        verify(mapperService).toCardEntity(inputDto);
        verify(cardRepo).save(entity);
        verify(mapperService, never()).toCardDto(any());
    }


    @Test
    void updateCard_WithExistingId_ShouldUpdateAndReturnDto() {

        SuspiciousCardTransferDto updateDto = createTestCardDto();
        updateDto.setBlocked(true);

        SuspiciousCardTransfer existingEntity = createTestCardEntity();
        SuspiciousCardTransfer updatedEntity = createTestCardEntity();
        updatedEntity.setBlocked(true);

        SuspiciousCardTransferDto expectedDto = createTestCardDto();
        expectedDto.setBlocked(true);

        when(cardRepo.findById(TEST_ID)).thenReturn(Optional.of(existingEntity));
        when(cardRepo.save(existingEntity)).thenReturn(updatedEntity);
        when(mapperService.toCardDto(updatedEntity)).thenReturn(expectedDto);

        SuspiciousCardTransferDto result = service.updateCard(TEST_ID, updateDto);

        assertNotNull(result);
        assertTrue(result.isBlocked());
        assertTrue(existingEntity.isBlocked());

        verify(cardRepo).findById(TEST_ID);
        verify(cardRepo).save(existingEntity);
        verify(mapperService).toCardDto(updatedEntity);
    }

    @Test
    void updateCard_WithNonExistingId_ShouldThrowException() {

        SuspiciousCardTransferDto updateDto = createTestCardDto();

        when(cardRepo.findById(TEST_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.updateCard(TEST_ID, updateDto));

        verify(cardRepo).findById(TEST_ID);
        verify(cardRepo, never()).save(any());
        verify(mapperService, never()).toCardDto(any());
    }

    @Test
    void getAllCards_ShouldReturnListOfDtos() {

        List<SuspiciousCardTransfer> entities = List.of(
                createTestCardEntity(),
                createTestCardEntity()
        );
        SuspiciousCardTransferDto expectedDto = createTestCardDto();

        when(cardRepo.findAll()).thenReturn(entities);
        when(mapperService.toCardDto(any(SuspiciousCardTransfer.class))).thenReturn(expectedDto);

        List<SuspiciousCardTransferDto> result = service.getAllCards();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedDto.getId(), result.get(0).getId());

        verify(cardRepo).findAll();
        verify(mapperService, times(2)).toCardDto(any(SuspiciousCardTransfer.class));
    }

    @Test
    void getAllCards_WhenNoData_ShouldReturnEmptyList() {

        when(cardRepo.findAll()).thenReturn(List.of());

        List<SuspiciousCardTransferDto> result = service.getAllCards();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(cardRepo).findAll();
        verify(mapperService, never()).toCardDto(any());
    }

    @Test
    void getCardById_WithExistingId_ShouldReturnDto() {

        SuspiciousCardTransfer entity = createTestCardEntity();
        SuspiciousCardTransferDto expectedDto = createTestCardDto();

        when(cardRepo.findById(TEST_ID)).thenReturn(Optional.of(entity));
        when(mapperService.toCardDto(entity)).thenReturn(expectedDto);

        SuspiciousCardTransferDto result = service.getCardById(TEST_ID);

        assertNotNull(result);
        assertEquals(expectedDto.getId(), result.getId());

        verify(cardRepo).findById(TEST_ID);
        verify(mapperService).toCardDto(entity);
    }

    @Test
    void getCardById_WithNonExistingId_ShouldThrowException() {
        when(cardRepo.findById(TEST_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.getCardById(TEST_ID));

        verify(cardRepo).findById(TEST_ID);
        verify(mapperService, never()).toCardDto(any());
    }

    @Test
    void deleteSuspiciousTransfer_WithCardType_ShouldDeleteCard() {
        doNothing().when(cardRepo).deleteById(TEST_ID);

        service.deleteSuspiciousTransfer(TEST_ID, "card");

        verify(cardRepo).deleteById(TEST_ID);
        verify(phoneRepo, never()).deleteById(any());
        verify(accountRepo, never()).deleteById(any());
        verify(mapperService, never()).toCardDto(any());
    }

    @Test
    void deleteSuspiciousTransfer_WithPhoneType_ShouldDeletePhone() {
        doNothing().when(phoneRepo).deleteById(TEST_ID);

        service.deleteSuspiciousTransfer(TEST_ID, "phone");

        verify(phoneRepo).deleteById(TEST_ID);
        verify(cardRepo, never()).deleteById(any());
        verify(accountRepo, never()).deleteById(any());
    }

    @Test
    void deleteSuspiciousTransfer_WithAccountType_ShouldDeleteAccount() {
        doNothing().when(accountRepo).deleteById(TEST_ID);

        service.deleteSuspiciousTransfer(TEST_ID, "account");

        verify(accountRepo).deleteById(TEST_ID);
        verify(cardRepo, never()).deleteById(any());
        verify(phoneRepo, never()).deleteById(any());
    }

    @Test
    void deleteSuspiciousTransfer_WithInvalidType_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class,
                () -> service.deleteSuspiciousTransfer(TEST_ID, "invalid"));
        verify(cardRepo, never()).deleteById(any());
        verify(phoneRepo, never()).deleteById(any());
        verify(accountRepo, never()).deleteById(any());
    }

    @Test
    void createPhone_WithValidDto_ShouldReturnSavedDto() {
        SuspiciousPhoneTransferDto inputDto = createTestPhoneDto();
        SuspiciousPhoneTransfer entity = createTestPhoneEntity();
        SuspiciousPhoneTransfer savedEntity = createTestPhoneEntity();
        SuspiciousPhoneTransferDto expectedDto = createTestPhoneDto();

        when(mapperService.toPhoneEntity(inputDto)).thenReturn(entity);
        when(phoneRepo.save(entity)).thenReturn(savedEntity);
        when(mapperService.toPhoneDto(savedEntity)).thenReturn(expectedDto);

        SuspiciousPhoneTransferDto result = service.createPhone(inputDto);

        assertNotNull(result);
        assertEquals(expectedDto.getId(), result.getId());

        verify(mapperService).toPhoneEntity(inputDto);
        verify(phoneRepo).save(entity);
        verify(mapperService).toPhoneDto(savedEntity);
    }

    @Test
    void getAllPhones_ShouldReturnListOfDtos() {
        List<SuspiciousPhoneTransfer> entities = List.of(createTestPhoneEntity());
        SuspiciousPhoneTransferDto expectedDto = createTestPhoneDto();

        when(phoneRepo.findAll()).thenReturn(entities);
        when(mapperService.toPhoneDto(any(SuspiciousPhoneTransfer.class))).thenReturn(expectedDto);

        List<SuspiciousPhoneTransferDto> result = service.getAllPhones();

        assertNotNull(result);
        assertEquals(1, result.size());

        verify(phoneRepo).findAll();
        verify(mapperService).toPhoneDto(any(SuspiciousPhoneTransfer.class));
    }

    @Test
    void getPhoneById_WithExistingId_ShouldReturnDto() {
        SuspiciousPhoneTransfer entity = createTestPhoneEntity();
        SuspiciousPhoneTransferDto expectedDto = createTestPhoneDto();

        when(phoneRepo.findById(TEST_ID)).thenReturn(Optional.of(entity));
        when(mapperService.toPhoneDto(entity)).thenReturn(expectedDto);

        SuspiciousPhoneTransferDto result = service.getPhoneById(TEST_ID);

        assertNotNull(result);
        assertEquals(expectedDto.getId(), result.getId());

        verify(phoneRepo).findById(TEST_ID);
        verify(mapperService).toPhoneDto(entity);
    }

    private SuspiciousCardTransferDto createTestCardDto() {
        return SuspiciousCardTransferDto.builder()
                .id(TEST_ID)
                .blocked(BLOCKED_TRUE)
                .suspicious(SUSPICIOUS_TRUE)
                .blockedReason(BLOCKED_REASON)
                .suspiciousReason(SUSPICIOUS_REASON)
                .build();
    }

    private SuspiciousCardTransfer createTestCardEntity() {
        return SuspiciousCardTransfer.builder()
                .id(TEST_ID)
                .blocked(BLOCKED_TRUE)
                .suspicious(SUSPICIOUS_TRUE)
                .blockedReason(BLOCKED_REASON)
                .suspiciousReason(SUSPICIOUS_REASON)
                .build();
    }

    private SuspiciousPhoneTransferDto createTestPhoneDto() {
        return SuspiciousPhoneTransferDto.builder()
                .id(TEST_ID)
                .blocked(BLOCKED_TRUE)
                .suspicious(SUSPICIOUS_TRUE)
                .blockedReason(BLOCKED_REASON)
                .suspiciousReason(SUSPICIOUS_REASON)
                .build();
    }

    private SuspiciousPhoneTransfer createTestPhoneEntity() {
        return SuspiciousPhoneTransfer.builder()
                .id(TEST_ID)
                .blocked(BLOCKED_TRUE)
                .suspicious(SUSPICIOUS_TRUE)
                .blockedReason(BLOCKED_REASON)
                .suspiciousReason(SUSPICIOUS_REASON)
                .build();
    }

    private SuspiciousAccountTransferDto createTestAccountDto() {
        return SuspiciousAccountTransferDto.builder()
                .id(TEST_ID)
                .blocked(BLOCKED_TRUE)
                .suspicious(SUSPICIOUS_TRUE)
                .blockedReason(BLOCKED_REASON)
                .suspiciousReason(SUSPICIOUS_REASON)
                .build();
    }

    private SuspiciousAccountTransfer createTestAccountEntity() {
        return SuspiciousAccountTransfer.builder()
                .id(TEST_ID)
                .blocked(BLOCKED_TRUE)
                .suspicious(SUSPICIOUS_TRUE)
                .blockedReason(BLOCKED_REASON)
                .suspiciousReason(SUSPICIOUS_REASON)
                .build();
    }
}