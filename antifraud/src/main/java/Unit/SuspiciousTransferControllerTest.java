package Unit;

import controller.SuspiciousTransferController;
import dto.SuspiciousAccountTransferDto;
import dto.SuspiciousCardTransferDto;
import dto.SuspiciousPhoneTransferDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import service.SuspiciousTransferService;

import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SuspiciousTransferControllerTest {

    @Mock
    private SuspiciousTransferService service;

    @InjectMocks
    private SuspiciousTransferController controller;

    private final Long TEST_ID = 1L;
    private final boolean BLOCKED_TRUE = true;
    private final boolean SUSPICIOUS_TRUE = true;
    private final String SUSPICIOUS_REASON = "Подозрительные действия";
    private final String BLOCKED_REASON = "Причина блокировки";


    @Test
    void createCard_WithValidDto_ShouldReturnCreated() {
        SuspiciousCardTransferDto inputDto = createTestCardDto();
        SuspiciousCardTransferDto expectedDto = createTestCardDto();

        when(service.createCard(inputDto)).thenReturn(expectedDto);

        ResponseEntity<SuspiciousCardTransferDto> response = controller.createCard(inputDto);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedDto.getId(), response.getBody().getId());

        verify(service).createCard(inputDto);
    }

    @Test
    void createPhone_WithValidDto_ShouldReturnCreated() {
        SuspiciousPhoneTransferDto inputDto = createTestPhoneDto();
        SuspiciousPhoneTransferDto expectedDto = createTestPhoneDto();

        when(service.createPhone(inputDto)).thenReturn(expectedDto);

        ResponseEntity<SuspiciousPhoneTransferDto> response = controller.createPhone(inputDto);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedDto.getId(), response.getBody().getId());

        verify(service).createPhone(inputDto);
    }

    @Test
    void createAccount_WithValidDto_ShouldReturnCreated() {
        SuspiciousAccountTransferDto inputDto = createTestAccountDto();
        SuspiciousAccountTransferDto expectedDto = createTestAccountDto();

        when(service.createAccount(inputDto)).thenReturn(expectedDto);

        ResponseEntity<SuspiciousAccountTransferDto> response = controller.createAccount(inputDto);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedDto.getId(), response.getBody().getId());

        verify(service).createAccount(inputDto);
    }

    @Test
    void updateCard_WithValidData_ShouldReturnOk() {

        SuspiciousCardTransferDto inputDto = createTestCardDto();
        SuspiciousCardTransferDto expectedDto = createTestCardDto();
        expectedDto.setBlocked(true);

        when(service.updateCard(TEST_ID, inputDto)).thenReturn(expectedDto);

        ResponseEntity<SuspiciousCardTransferDto> response = controller.updateCard(TEST_ID, inputDto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isBlocked());

        verify(service).updateCard(TEST_ID, inputDto);
    }

    @Test
    void updatePhone_WithValidData_ShouldReturnOk() {

        SuspiciousPhoneTransferDto inputDto = createTestPhoneDto();
        SuspiciousPhoneTransferDto expectedDto = createTestPhoneDto();

        when(service.updatePhone(TEST_ID, inputDto)).thenReturn(expectedDto);

        ResponseEntity<SuspiciousPhoneTransferDto> response = controller.updatePhone(TEST_ID, inputDto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        verify(service).updatePhone(TEST_ID, inputDto);
    }

    @Test
    void deleteCard_ShouldReturnNoContent() {
        doNothing().when(service).deleteSuspiciousTransfer(TEST_ID, "card");

        ResponseEntity<Void> response = controller.deleteCard(TEST_ID);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());

        verify(service).deleteSuspiciousTransfer(TEST_ID, "card");
    }

    @Test
    void deletePhone_ShouldReturnNoContent() {
        doNothing().when(service).deleteSuspiciousTransfer(TEST_ID, "phone");

        ResponseEntity<Void> response = controller.deletePhone(TEST_ID);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(service).deleteSuspiciousTransfer(TEST_ID, "phone");
    }

    @Test
    void getAllCards_ShouldReturnList() {
        List<SuspiciousCardTransferDto> expectedList = List.of(createTestCardDto());

        when(service.getAllCards()).thenReturn(expectedList);

        ResponseEntity<List<SuspiciousCardTransferDto>> response = controller.getAllCards();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(expectedList.get(0).getId(), response.getBody().get(0).getId());

        verify(service).getAllCards();
    }

    @Test
    void getAllPhones_ShouldReturnList() {
        List<SuspiciousPhoneTransferDto> expectedList = List.of(createTestPhoneDto());

        when(service.getAllPhones()).thenReturn(expectedList);

        ResponseEntity<List<SuspiciousPhoneTransferDto>> response = controller.getAllPhones();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());

        verify(service).getAllPhones();
    }

    @Test
    void getAllAccounts_ShouldReturnList() {
        List<SuspiciousAccountTransferDto> expectedList = List.of(createTestAccountDto());

        when(service.getAllAccounts()).thenReturn(expectedList);

        ResponseEntity<List<SuspiciousAccountTransferDto>> response = controller.getAllAccounts();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());

        verify(service).getAllAccounts();
    }


    @Test
    void getCardById_WithExistingId_ShouldReturnCard() {
        SuspiciousCardTransferDto expectedDto = createTestCardDto();

        when(service.getCardById(TEST_ID)).thenReturn(expectedDto);

        ResponseEntity<SuspiciousCardTransferDto> response = controller.getCardById(TEST_ID);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedDto.getId(), response.getBody().getId());

        verify(service).getCardById(TEST_ID);
    }

    @Test
    void getPhoneById_WithExistingId_ShouldReturnPhone() {

        SuspiciousPhoneTransferDto expectedDto = createTestPhoneDto();
        when(service.getPhoneById(TEST_ID)).thenReturn(expectedDto);

        ResponseEntity<SuspiciousPhoneTransferDto> response = controller.getPhoneById(TEST_ID);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedDto.getId(), response.getBody().getId());

        verify(service).getPhoneById(TEST_ID);
    }

    @Test
    void getAccountById_WithExistingId_ShouldReturnAccount() {

        SuspiciousAccountTransferDto expectedDto = createTestAccountDto();

        when(service.getAccountById(TEST_ID)).thenReturn(expectedDto);

        ResponseEntity<SuspiciousAccountTransferDto> response = controller.getAccountById(TEST_ID);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedDto.getId(), response.getBody().getId());

        verify(service).getAccountById(TEST_ID);
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

    private SuspiciousPhoneTransferDto createTestPhoneDto() {
        return SuspiciousPhoneTransferDto.builder()
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
}


