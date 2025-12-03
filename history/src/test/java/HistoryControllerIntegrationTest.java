import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@Slf4j
class HistoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createAndRetrieveHistory_IntegrationTest() throws Exception {
        // Create history
        String historyJson = """
            {
                "transferAuditId": 100,
                "profileAuditId": 200,
                "accountAuditId": 300,
                "antiFraudAuditId": 400,
                "publicBankInfoAuditId": 500,
                "authorizationAuditId": 600
            }
            """;

        mockMvc.perform(post("/api/history")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(historyJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.accountAuditId").value(300));

        // Try to get all histories
        mockMvc.perform(get("/api/history"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getNonExistentHistory_ReturnsNotFound() throws Exception {
        mockMvc.perform(get("/api/history/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAggregatedHistory_IntegrationTest() throws Exception {
        mockMvc.perform(get("/api/history/aggregate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}
