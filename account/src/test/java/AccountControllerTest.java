import Controller.AccountController;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.AccountDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import service.AccountService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Autowired
    private ObjectMapper objectMapper;

    private AccountDto createTestAccountDto() {
        return AccountDto.builder()
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
    void createAccount_Success() throws Exception {
        // Given
        AccountDto accountDto = createTestAccountDto();
        when(accountService.createAccount(any(AccountDto.class), eq("testUser"))).thenReturn(accountDto);

        // When & Then
        mockMvc.perform(post("/api/accounts")
                        .header("X-User-Id", "testUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.entityType").value("USER"));

        verify(accountService, times(1)).createAccount(any(AccountDto.class), eq("testUser"));
    }

    @Test
    void getAccountById_WhenExists_ReturnsAccount() throws Exception {
        // Given
        AccountDto accountDto = createTestAccountDto();
        when(accountService.findById(1L)).thenReturn(Optional.of(accountDto));

        // When & Then
        mockMvc.perform(get("/api/accounts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.entityType").value("USER"));

        verify(accountService, times(1)).findById(1L);
    }

    @Test
    void getAccountById_WhenNotExists_ReturnsNotFound() throws Exception {
        // Given
        when(accountService.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/api/accounts/999"))
                .andExpect(status().isNotFound());

        verify(accountService, times(1)).findById(999L);
    }

    @Test
    void getAllAccounts_ReturnsAccountsList() throws Exception {
        // Given
        AccountDto accountDto = createTestAccountDto();
        when(accountService.findAll()).thenReturn(Arrays.asList(accountDto));

        // When & Then
        mockMvc.perform(get("/api/accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].entityType").value("USER"));

        verify(accountService, times(1)).findAll();
    }

    @Test
    void updateAccount_Success() throws Exception {
        // Given
        AccountDto accountDto = createTestAccountDto();
        when(accountService.updateAccount(eq(1L), any(AccountDto.class), eq("updater")))
                .thenReturn(accountDto);

        // When & Then
        mockMvc.perform(put("/api/accounts/1")
                        .header("X-User-Id", "updater")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(accountService, times(1)).updateAccount(eq(1L), any(AccountDto.class), eq("updater"));
    }

    @Test
    void deleteAccount_WhenExists_ReturnsNoContent() throws Exception {
        // Given
        when(accountService.deleteAccount(1L, "deleter")).thenReturn(true);

        // When & Then
        mockMvc.perform(delete("/api/accounts/1")
                        .header("X-User-Id", "deleter"))
                .andExpect(status().isNoContent());

        verify(accountService, times(1)).deleteAccount(1L, "deleter");
    }

    @Test
    void deleteAccount_WhenNotExists_ReturnsNotFound() throws Exception {
        // Given
        when(accountService.deleteAccount(999L, "deleter")).thenReturn(false);

        // When & Then
        mockMvc.perform(delete("/api/accounts/999")
                        .header("X-User-Id", "deleter"))
                .andExpect(status().isNotFound());

        verify(accountService, times(1)).deleteAccount(999L, "deleter");
    }
}