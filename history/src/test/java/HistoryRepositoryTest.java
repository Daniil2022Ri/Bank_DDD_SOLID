

import lombok.extern.slf4j.Slf4j;
import model.History;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import repository.HistoryRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@Slf4j
class HistoryRepositoryTest {

    @Autowired
    private HistoryRepository historyRepository;

    @Test
    void saveHistory_Success() {
        History history = History.builder()
                .transferAuditId(100L)
                .accountAuditId(200L)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        History savedHistory = historyRepository.save(history);

        assertNotNull(savedHistory.getId());
        assertEquals(100L, savedHistory.getTransferAuditId());
    }

    @Test
    void findByAccountAuditId_WhenExists_ReturnsHistory() {
        History history = History.builder()
                .accountAuditId(300L)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        historyRepository.save(history);

        Optional<History> found = historyRepository.findByAccountAuditId(300L);

        assertTrue(found.isPresent());
        assertEquals(300L, found.get().getAccountAuditId());
    }

    @Test
    void findByAccountAuditIdIsNotNull_ReturnsHistoriesWithAccountAudit() {
        History history1 = History.builder().accountAuditId(400L).build();
        History history2 = History.builder().accountAuditId(null).build();
        historyRepository.save(history1);
        historyRepository.save(history2);

        List<History> histories = historyRepository.findByAccountAuditIdIsNotNull();

        assertFalse(histories.isEmpty());
        assertTrue(histories.stream().allMatch(h -> h.getAccountAuditId() != null));
    }
}
