import com.fasterxml.jackson.databind.ObjectMapper;
import controller.HistoryController;
import dto.HistoryDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import service.HistoryService;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HistoryController.class)
@Slf4j
class HistoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HistoryService historyService;

    @Autowired
    private ObjectMapper objectMapper;

    private HistoryDto createTestHistoryDto() {
        return HistoryDto.builder()
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
    void createHistory_Success() throws Exception {
        HistoryDto historyDto = createTestHistoryDto();
        when(historyService.createHistory(any(HistoryDto.class))).thenReturn(historyDto);

        mockMvc.perform(post("/api/history")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(historyDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.accountAuditId").value(300L));

        verify(historyService, times(1)).createHistory(any(HistoryDto.class));
    }

    @Test
    void getHistoryById_WhenExists_ReturnsHistory() throws Exception {
        HistoryDto historyDto = createTestHistoryDto();
        when(historyService.getHistoryById(1L)).thenReturn(Optional.of(historyDto));

        mockMvc.perform(get("/api/history/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.transferAuditId").value(100L));

        verify(historyService, times(1)).getHistoryById(1L);
    }

    @Test
    void getHistoryById_WhenNotExists_ReturnsNotFound() throws Exception {
        when(historyService.getHistoryById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/history/999"))
                .andExpect(status().isNotFound());

        verify(historyService, times(1)).getHistoryById(999L);
    }

    @Test
    void getAllHistory_ReturnsHistoryList() throws Exception {
        HistoryDto historyDto = createTestHistoryDto();
        when(historyService.getAllHistory()).thenReturn(Arrays.asList(historyDto));

        mockMvc.perform(get("/api/history"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].profileAuditId").value(200L));

        verify(historyService, times(1)).getAllHistory();
    }

    @Test
    void getAggregatedHistory_ReturnsAggregatedData() throws Exception {
        HistoryDto historyDto = createTestHistoryDto();
        when(historyService.getAggregatedHistory()).thenReturn(Arrays.asList(historyDto));

        mockMvc.perform(get("/api/history/aggregate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));

        verify(historyService, times(1)).getAggregatedHistory();
    }

    @Test
    void getHistoryByMicroservice_ForAccount_ReturnsAccountHistory() throws Exception {
        HistoryDto historyDto = createTestHistoryDto();
        when(historyService.getHistoryByMicroservice("account")).thenReturn(Arrays.asList(historyDto));

        mockMvc.perform(get("/api/history/microservice/account"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));

        verify(historyService, times(1)).getHistoryByMicroservice("account");
    }

    @Test
    void updateHistory_Success() throws Exception {
        HistoryDto historyDto = createTestHistoryDto();
        when(historyService.updateHistory(eq(1L), any(HistoryDto.class))).thenReturn(historyDto);

        mockMvc.perform(put("/api/history/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(historyDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(historyService, times(1)).updateHistory(eq(1L), any(HistoryDto.class));
    }

    @Test
    void deleteHistory_WhenExists_ReturnsNoContent() throws Exception {
        doNothing().when(historyService).deleteHistory(1L);

        mockMvc.perform(delete("/api/history/1"))
                .andExpect(status().isNoContent());

        verify(historyService, times(1)).deleteHistory(1L);
    }

    @Test
    void findByAccountAuditId_WhenExists_ReturnsHistory() throws Exception {
        HistoryDto historyDto = createTestHistoryDto();
        when(historyService.findByAccountAuditId(100L)).thenReturn(Optional.of(historyDto));

        mockMvc.perform(get("/api/history/account-audit/100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(historyService, times(1)).findByAccountAuditId(100L);
    }
}
