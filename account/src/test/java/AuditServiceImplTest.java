
import dto.AuditDto;
import lombok.extern.slf4j.Slf4j;
import mappers.AuditMapper;
import model.Account;
import model.Audit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import repositories.AuditRepository;
import service.AuditServiceImpl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
class AuditServiceImplTest {

    @Mock
    private AuditRepository auditRepository;

    @Spy
    private AuditMapper auditMapper;

    @InjectMocks
    private AuditServiceImpl auditService;

    private Audit testAudit;
    private AuditDto testAuditDto;
    private Account testAccount;

    @BeforeEach
    void setUp() {
        testAudit = Audit.builder()
                .id(1L)
                .passwordId(123L)
                .accountNumber(456L)
                .bankDetailsId(789L)
                .negativeBalance(false)
                .profileId(999L)
                .build();

        testAuditDto = AuditDto.builder()
                .id(1L)
                .passwordId(123L)
                .accountNumber(456L)
                .bankDetailsId(789L)
                .negativeBalance(false)
                .profileId(999L)
                .build();

        testAccount = Account.builder()
                .id(1L)
                .entityType("USER")
                .operationType("CREATE")
                .createdBy("testUser")
                .build();
    }

    @Test
    void logAccountCreation_Success() {
        // Given
        when(auditRepository.save(any(Audit.class))).thenReturn(testAudit);

        // When
        auditService.logAccountCreation(testAccount, "testUser");

        // Then
        verify(auditRepository, times(1)).save(any(Audit.class));

        log.info("logAccountCreation_Success test passed");
    }

    @Test
    void logAccountUpdate_Success() {
        // Given
        when(auditRepository.save(any(Audit.class))).thenReturn(testAudit);

        // When
        auditService.logAccountUpdate(testAccount, testAccount, "updater");

        // Then
        verify(auditRepository, times(1)).save(any(Audit.class));

        log.info("logAccountUpdate_Success test passed");
    }

    @Test
    void logAccountDeletion_Success() {
        // Given
        when(auditRepository.save(any(Audit.class))).thenReturn(testAudit);

        // When
        auditService.logAccountDeletion(testAccount, "deleter");

        // Then
        verify(auditRepository, times(1)).save(any(Audit.class));

        log.info("logAccountDeletion_Success test passed");
    }

    @Test
    void createAudit_Success() {
        // Given
        when(auditRepository.save(any(Audit.class))).thenReturn(testAudit);

        // When
        AuditDto result = auditService.createAudit(testAuditDto);

        // Then
        assertNotNull(result);
        assertEquals(testAuditDto.getPasswordId(), result.getPasswordId());
        verify(auditRepository, times(1)).save(any(Audit.class));

        log.info("createAudit_Success test passed");
    }

    @Test
    void findAuditById_WhenAuditExists_ReturnsAudit() {
        // Given
        when(auditRepository.findById(1L)).thenReturn(Optional.of(testAudit));

        // When
        Optional<AuditDto> result = auditService.findAuditById(1L);

        // Then
        assertTrue(result.isPresent());
        assertEquals(testAudit.getId(), result.get().getId());
        verify(auditRepository, times(1)).findById(1L);

        log.info("findAuditById_WhenAuditExists_ReturnsAudit test passed");
    }

    @Test
    void findAuditById_WhenAuditNotExists_ReturnsEmpty() {
        // Given
        when(auditRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        Optional<AuditDto> result = auditService.findAuditById(999L);

        // Then
        assertFalse(result.isPresent());
        verify(auditRepository, times(1)).findById(999L);

        log.info("findAuditById_WhenAuditNotExists_ReturnsEmpty test passed");
    }

    @Test
    void findAllAudits_ReturnsAllAudits() {
        // Given
        List<Audit> audits = Arrays.asList(testAudit, testAudit);
        when(auditRepository.findAll()).thenReturn(audits);

        // When
        List<AuditDto> result = auditService.findAllAudits();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(auditRepository, times(1)).findAll();

        log.info("findAllAudits_ReturnsAllAudits test passed");
    }

    @Test
    void findByPasswordId_ReturnsFilteredAudits() {
        // Given
        List<Audit> audits = Arrays.asList(testAudit);
        when(auditRepository.findByPasswordId(123L)).thenReturn(audits);

        // When
        List<AuditDto> result = auditService.findByPasswordId(123L);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(auditRepository, times(1)).findByPasswordId(123L);

        log.info("findByPasswordId_ReturnsFilteredAudits test passed");
    }

    @Test
    void findByAccountNumber_ReturnsFilteredAudits() {
        // Given
        List<Audit> audits = Arrays.asList(testAudit);
        when(auditRepository.findByAccountNumber(456L)).thenReturn(audits);

        // When
        List<AuditDto> result = auditService.findByAccountNumber(456L);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(auditRepository, times(1)).findByAccountNumber(456L);

        log.info("findByAccountNumber_ReturnsFilteredAudits test passed");
    }

    @Test
    void findByProfileId_ReturnsFilteredAudits() {
        // Given
        List<Audit> audits = Arrays.asList(testAudit);
        when(auditRepository.findByProfileId(999L)).thenReturn(audits);

        // When
        List<AuditDto> result = auditService.findByProfileId(999L);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(auditRepository, times(1)).findByProfileId(999L);

        log.info("findByProfileId_ReturnsFilteredAudits test passed");
    }

    @Test
    void findByNegativeBalance_ReturnsFilteredAudits() {
        // Given
        List<Audit> audits = Arrays.asList(testAudit);
        when(auditRepository.findByNegativeBalance(false)).thenReturn(audits);

        // When
        List<AuditDto> result = auditService.findByNegativeBalance(false);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(auditRepository, times(1)).findByNegativeBalance(false);

        log.info("findByNegativeBalance_ReturnsFilteredAudits test passed");
    }
}
