

import ExceptionHandling.EntityNotFoundException;
import dto.AccountDto;
import lombok.extern.slf4j.Slf4j;
import mappers.AccountMapper;
import model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import repositories.AccountRepository;
import service.AccountServiceImpl;
import service.AuditService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AuditService auditService;

    @Spy
    private AccountMapper accountMapper;

    @InjectMocks
    private AccountServiceImpl accountService;

    private Account testAccount;
    private AccountDto testAccountDto;

    @BeforeEach
    void setUp() {
        testAccount = Account.builder()
                .id(1L)
                .entityType("USER")
                .operationType("CREATE")
                .createdBy("testUser")
                .modifiedBy("testUser")
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .newEntityJson("{\"name\":\"John\"}")
                .entityJson("{\"name\":\"John Doe\"}")
                .build();

        testAccountDto = AccountDto.builder()
                .id(1L)
                .entityType("USER")
                .operationType("CREATE")
                .createdBy("testUser")
                .modifiedBy("testUser")
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .newEntityJson("{\"name\":\"John\"}")
                .entityJson("{\"name\":\"John Doe\"}")
                .build();
    }

    @Test
    void createAccount_Success() {
        // Given
        when(accountRepository.save(any(Account.class))).thenReturn(testAccount);

        // When
        AccountDto result = accountService.createAccount(testAccountDto, "testUser");

        // Then
        assertNotNull(result);
        assertEquals(testAccountDto.getEntityType(), result.getEntityType());
        assertEquals(testAccountDto.getOperationType(), result.getOperationType());
        verify(accountRepository, times(1)).save(any(Account.class));
        verify(auditService, times(1)).logAccountCreation(any(Account.class), eq("testUser"));

        log.info("createAccount_Success test passed");
    }

    @Test
    void findById_WhenAccountExists_ReturnsAccount() {
        // Given
        when(accountRepository.findById(1L)).thenReturn(Optional.of(testAccount));

        // When
        Optional<AccountDto> result = accountService.findById(1L);

        // Then
        assertTrue(result.isPresent());
        assertEquals(testAccount.getId(), result.get().getId());
        verify(accountRepository, times(1)).findById(1L);

        log.info("findById_WhenAccountExists_ReturnsAccount test passed");
    }

    @Test
    void findById_WhenAccountNotExists_ReturnsEmpty() {
        // Given
        when(accountRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        Optional<AccountDto> result = accountService.findById(999L);

        // Then
        assertFalse(result.isPresent());
        verify(accountRepository, times(1)).findById(999L);

        log.info("findById_WhenAccountNotExists_ReturnsEmpty test passed");
    }

    @Test
    void findAll_ReturnsAllAccounts() {
        // Given
        List<Account> accounts = Arrays.asList(testAccount, testAccount);
        when(accountRepository.findAll()).thenReturn(accounts);

        // When
        List<AccountDto> result = accountService.findAll();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(accountRepository, times(1)).findAll();

        log.info("findAll_ReturnsAllAccounts test passed");
    }

    @Test
    void updateAccount_WhenAccountExists_Success() {
        // Given
        AccountDto updateDto = AccountDto.builder()
                .entityType("UPDATED_USER")
                .operationType("UPDATE")
                .newEntityJson("{\"name\":\"Updated John\"}")
                .build();

        when(accountRepository.findById(1L)).thenReturn(Optional.of(testAccount));
        when(accountRepository.save(any(Account.class))).thenReturn(testAccount);

        // When
        AccountDto result = accountService.updateAccount(1L, updateDto, "updatedUser");

        // Then
        assertNotNull(result);
        verify(accountRepository, times(1)).findById(1L);
        verify(accountRepository, times(1)).save(any(Account.class));
        verify(auditService, times(1)).logAccountUpdate(any(Account.class), any(Account.class), eq("updatedUser"));

        log.info("updateAccount_WhenAccountExists_Success test passed");
    }

    @Test
    void updateAccount_WhenAccountNotExists_ThrowsException() {
        // Given
        when(accountRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(EntityNotFoundException.class, () -> {
            accountService.updateAccount(999L, testAccountDto, "testUser");
        });

        verify(accountRepository, times(1)).findById(999L);
        verify(accountRepository, never()).save(any(Account.class));

        log.info("updateAccount_WhenAccountNotExists_ThrowsException test passed");
    }

    @Test
    void deleteAccount_WhenAccountExists_ReturnsTrue() {
        // Given
        when(accountRepository.findById(1L)).thenReturn(Optional.of(testAccount));

        // When
        boolean result = accountService.deleteAccount(1L, "deleter");

        // Then
        assertTrue(result);
        verify(accountRepository, times(1)).delete(testAccount);
        verify(auditService, times(1)).logAccountDeletion(eq(testAccount), eq("deleter"));

        log.info("deleteAccount_WhenAccountExists_ReturnsTrue test passed");
    }

    @Test
    void deleteAccount_WhenAccountNotExists_ReturnsFalse() {
        // Given
        when(accountRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        boolean result = accountService.deleteAccount(999L, "deleter");

        // Then
        assertFalse(result);
        verify(accountRepository, never()).delete(any(Account.class));

        log.info("deleteAccount_WhenAccountNotExists_ReturnsFalse test passed");
    }

    @Test
    void findByEntityType_ReturnsFilteredAccounts() {
        // Given
        List<Account> accounts = Arrays.asList(testAccount);
        when(accountRepository.findByEntityType("USER")).thenReturn(accounts);

        // When
        List<AccountDto> result = accountService.findByEntityType("USER");

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(accountRepository, times(1)).findByEntityType("USER");

        log.info("findByEntityType_ReturnsFilteredAccounts test passed");
    }

    @Test
    void findByCreatedBy_ReturnsFilteredAccounts() {
        // Given
        List<Account> accounts = Arrays.asList(testAccount);
        when(accountRepository.findByCreatedBy("testUser")).thenReturn(accounts);

        // When
        List<AccountDto> result = accountService.findByCreatedBy("testUser");

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(accountRepository, times(1)).findByCreatedBy("testUser");

        log.info("findByCreatedBy_ReturnsFilteredAccounts test passed");
    }

    @Test
    void findByOperationType_ReturnsFilteredAccounts() {
        // Given
        List<Account> accounts = Arrays.asList(testAccount);
        when(accountRepository.findByOperationType("CREATE")).thenReturn(accounts);

        // When
        List<AccountDto> result = accountService.findByOperationType("CREATE");

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(accountRepository, times(1)).findByOperationType("CREATE");

        log.info("findByOperationType_ReturnsFilteredAccounts test passed");
    }
}