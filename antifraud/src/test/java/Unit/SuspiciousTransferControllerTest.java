package Unit;

import config.TestConstants;
import controller.SuspiciousTransferController;
import dto.SuspiciousAccountTransferDto;
import dto.SuspiciousCardTransferDto;
import dto.SuspiciousPhoneTransferDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import service.SuspiciousTransferService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Тесты SuspiciousTransferController")
class SuspiciousTransferControllerTest {

    @Mock
    private SuspiciousTransferService service;

    @InjectMocks
    private SuspiciousTransferController controller;

    @Test
    @DisplayName("POST /card — успешное создание")
    void createCard_success() {
        SuspiciousCardTransferDto dto = cardDto();
        when(service.createCard(dto)).thenReturn(dto);

        ResponseEntity<SuspiciousCardTransferDto> response = controller.createCard(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(dto, response.getBody());
        verify(service).createCard(dto);
    }

    @Test
    @DisplayName("POST /phone — успешное создание")
    void createPhone_success() {
        SuspiciousPhoneTransferDto dto = phoneDto();
        when(service.createPhone(dto)).thenReturn(dto);

        ResponseEntity<SuspiciousPhoneTransferDto> response = controller.createPhone(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(dto, response.getBody());
        verify(service).createPhone(dto);
    }

    @Test
    @DisplayName("POST /account — успешное создание")
    void createAccount_success() {
        SuspiciousAccountTransferDto dto = accountDto();
        when(service.createAccount(dto)).thenReturn(dto);

        ResponseEntity<SuspiciousAccountTransferDto> response = controller.createAccount(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(dto, response.getBody());
        verify(service).createAccount(dto);
    }

    @Test
    @DisplayName("PUT /card/{id} — успешное обновление")
    void updateCard_success() {
        SuspiciousCardTransferDto dto = cardDto().toBuilder().blocked(true).build();
        when(service.updateCard(TestConstants.TEST_ID, dto)).thenReturn(dto);

        ResponseEntity<SuspiciousCardTransferDto> response = controller.updateCard(TestConstants.TEST_ID, dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isBlocked());
        verify(service).updateCard(TestConstants.TEST_ID, dto);
    }

    @Test
    @DisplayName("DELETE /card/{id} — успешное удаление")
    void deleteCard_success() {
        doNothing().when(service).deleteSuspiciousTransfer(TestConstants.TEST_ID, "card");

        ResponseEntity<Void> response = controller.deleteCard(TestConstants.TEST_ID);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service).deleteSuspiciousTransfer(TestConstants.TEST_ID, "card");
    }

    @Test
    @DisplayName("GET /card — получение списка")
    void getAllCards_returnsList() {
        List<SuspiciousCardTransferDto> list = List.of(cardDto());
        when(service.getAllCards()).thenReturn(list);

        ResponseEntity<List<SuspiciousCardTransferDto>> response = controller.getAllCards();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(service).getAllCards();
    }

    @Test
    @DisplayName("GET /card/{id} — получение по ID")
    void getCardById_success() {
        SuspiciousCardTransferDto dto = cardDto();
        when(service.getCardById(TestConstants.TEST_ID)).thenReturn(dto);

        ResponseEntity<SuspiciousCardTransferDto> response = controller.getCardById(TestConstants.TEST_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
        verify(service).getCardById(TestConstants.TEST_ID);
    }

    private SuspiciousCardTransferDto cardDto() {
        return SuspiciousCardTransferDto.builder()
                .id(TestConstants.TEST_ID)
                .blocked(TestConstants.BLOCKED_FALSE)
                .suspicious(TestConstants.SUSPICIOUS_TRUE)
                .blockedReason(TestConstants.BLOCKED_REASON)
                .suspiciousReason(TestConstants.SUSPICIOUS_REASON)
                .build();
    }

    private SuspiciousPhoneTransferDto phoneDto() {
        return SuspiciousPhoneTransferDto.builder()
                .id(TestConstants.TEST_ID)
                .blocked(TestConstants.BLOCKED_FALSE)
                .suspicious(TestConstants.SUSPICIOUS_TRUE)
                .blockedReason(TestConstants.BLOCKED_REASON)
                .suspiciousReason(TestConstants.SUSPICIOUS_REASON)
                .build();
    }

    private SuspiciousAccountTransferDto accountDto() {
        return SuspiciousAccountTransferDto.builder()
                .id(TestConstants.TEST_ID)
                .blocked(TestConstants.BLOCKED_FALSE)
                .suspicious(TestConstants.SUSPICIOUS_TRUE)
                .blockedReason(TestConstants.BLOCKED_REASON)
                .suspiciousReason(TestConstants.SUSPICIOUS_REASON)
                .build();
    }
}