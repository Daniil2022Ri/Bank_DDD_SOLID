
import dto.HistoryDto;
import ExceptionHandling.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import mappers.HistoryMapper;
import model.History;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.HistoryRepository;
import service.HistoryServiceImpl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
class HistoryServiceImplTest {

    @Mock
    private HistoryRepository historyRepository;

    @Spy
    private HistoryMapper historyMapper;

    @InjectMocks
    private HistoryServiceImpl historyService;

    private History testHistory;
    private HistoryDto testHistoryDto;

    @BeforeEach
    void setUp() {
        testHistory = History.builder()
                .id(1L)
                .transferAuditId(100L)
                .profileAuditId(200L)
                .accountAuditId(300L)
                .antiFraudAuditId(400L)
                .publicBankInfoAuditId(500L)
                .authorizationAuditId(600L)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        testHistoryDto = HistoryDto.builder()
                .id(1L)
                .transferAuditId(100L)
                .profileAuditId(200L)
                .accountAuditId(300L)
                .antiFraudAuditId(400L)
                .publicBankInfoAuditId(500L)
                .authorizationAuditId(600L)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    void createHistory_Success() {
        when(historyRepository.save(any(History.class))).thenReturn(testHistory);

        HistoryDto result = historyService.createHistory(testHistoryDto);

        assertNotNull(result);
        assertEquals(testHistoryDto.getTransferAuditId(), result.getTransferAuditId());
        assertEquals(testHistoryDto.getAccountAuditId(), result.getAccountAuditId());
        verify(historyRepository, times(1)).save(any(History.class));

        log.info("createHistory_Success test passed");
    }

    @Test
    void getHistoryById_WhenHistoryExists_ReturnsHistory() {
        when(historyRepository.findById(1L)).thenReturn(Optional.of(testHistory));

        Optional<HistoryDto> result = historyService.getHistoryById(1L);

        assertTrue(result.isPresent());
        assertEquals(testHistory.getId(), result.get().getId());
        verify(historyRepository, times(1)).findById(1L);

        log.info("getHistoryById_WhenHistoryExists_ReturnsHistory test passed");
    }

    @Test
    void getHistoryById_WhenHistoryNotExists_ReturnsEmpty() {
        when(historyRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<HistoryDto> result = historyService.getHistoryById(999L);

        assertFalse(result.isPresent());
        verify(historyRepository, times(1)).findById(999L);

        log.info("getHistoryById_WhenHistoryNotExists_ReturnsEmpty test passed");
    }

    @Test
    void getAllHistory_ReturnsAllHistories() {
        List<History> histories = Arrays.asList(testHistory, testHistory);
        when(historyRepository.findAllOrderByCreatedAtDesc()).thenReturn(histories);

        List<HistoryDto> result = historyService.getAllHistory();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(historyRepository, times(1)).findAllOrderByCreatedAtDesc();

        log.info("getAllHistory_ReturnsAllHistories test passed");
    }

    @Test
    void updateHistory_WhenHistoryExists_Success() {
        HistoryDto updateDto = HistoryDto.builder()
                .transferAuditId(150L)
                .accountAuditId(350L)
                .build();

        when(historyRepository.findById(1L)).thenReturn(Optional.of(testHistory));
        when(historyRepository.save(any(History.class))).thenReturn(testHistory);

        HistoryDto result = historyService.updateHistory(1L, updateDto);

        assertNotNull(result);
        verify(historyRepository, times(1)).findById(1L);
        verify(historyRepository, times(1)).save(any(History.class));

        log.info("updateHistory_WhenHistoryExists_Success test passed");
    }

    @Test
    void updateHistory_WhenHistoryNotExists_ThrowsException() {
        HistoryDto updateDto = HistoryDto.builder().build();
        when(historyRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            historyService.updateHistory(999L, updateDto);
        });

        verify(historyRepository, times(1)).findById(999L);
        verify(historyRepository, never()).save(any(History.class));

        log.info("updateHistory_WhenHistoryNotExists_ThrowsException test passed");
    }

    @Test
    void deleteHistory_WhenHistoryExists_Success() {
        when(historyRepository.existsById(1L)).thenReturn(true);

        historyService.deleteHistory(1L);

        verify(historyRepository, times(1)).deleteById(1L);

        log.info("deleteHistory_WhenHistoryExists_Success test passed");
    }

    @Test
    void deleteHistory_WhenHistoryNotExists_ThrowsException() {
        when(historyRepository.existsById(999L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> {
            historyService.deleteHistory(999L);
        });

        verify(historyRepository, never()).deleteById(anyLong());

        log.info("deleteHistory_WhenHistoryNotExists_ThrowsException test passed");
    }

    @Test
    void getAggregatedHistory_ReturnsAllHistories() {
        List<History> histories = Arrays.asList(testHistory);
        when(historyRepository.findAllOrderByCreatedAtDesc()).thenReturn(histories);

        List<HistoryDto> result = historyService.getAggregatedHistory();

        assertNotNull(result);
        assertEquals(1, result.size());

        log.info("getAggregatedHistory_ReturnsAllHistories test passed");
    }

    @Test
    void getHistoryByMicroservice_ForAccount_ReturnsAccountHistories() {
        List<History> histories = Arrays.asList(testHistory);
        when(historyRepository.findByAccountAuditIdIsNotNull()).thenReturn(histories);

        List<HistoryDto> result = historyService.getHistoryByMicroservice("account");

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(historyRepository, times(1)).findByAccountAuditIdIsNotNull();

        log.info("getHistoryByMicroservice_ForAccount_ReturnsAccountHistories test passed");
    }

    @Test
    void getHistoryByMicroservice_ForUnknownMicroservice_ThrowsException() {
        assertThrows(ExceptionHandling.ValidationException.class, () -> {
            historyService.getHistoryByMicroservice("unknown");
        });

        log.info("getHistoryByMicroservice_ForUnknownMicroservice_ThrowsException test passed");
    }

    @Test
    void findByAccountAuditId_WhenExists_ReturnsHistory() {
        when(historyRepository.findByAccountAuditId(100L)).thenReturn(Optional.of(testHistory));

        Optional<HistoryDto> result = historyService.findByAccountAuditId(100L);

        assertTrue(result.isPresent());
        verify(historyRepository, times(1)).findByAccountAuditId(100L);

        log.info("findByAccountAuditId_WhenExists_ReturnsHistory test passed");
    }

    @Test
    void findByAccountAuditId_WhenNotExists_ReturnsEmpty() {
        when(historyRepository.findByAccountAuditId(999L)).thenReturn(Optional.empty());

        Optional<HistoryDto> result = historyService.findByAccountAuditId(999L);

        assertFalse(result.isPresent());
        verify(historyRepository, times(1)).findByAccountAuditId(999L);

        log.info("findByAccountAuditId_WhenNotExists_ReturnsEmpty test passed");
    }
}
