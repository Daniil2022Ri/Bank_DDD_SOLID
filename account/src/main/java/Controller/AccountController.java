package Controller;

import config.ApplicationConstants;
import dto.AccountDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@Slf4j
@Tag(name = ApplicationConstants.API_TAG_ACCOUNT, description = ApplicationConstants.API_DESCRIPTION_ACCOUNT)
public class AccountController {

    private final service.AccountService accountService;

    @PostMapping
    @Operation(summary = "Создать новую учетную запись",
            description = ApplicationConstants.SWAGGER_DESC_CREATE_ACCOUNT)
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Учетная запись успешно создана"),
            @ApiResponse(responseCode = "400", description = ApplicationConstants.INVALID_INPUT_DATA),
            @ApiResponse(responseCode = "409", description = ApplicationConstants.ACCOUNT_ALREADY_EXISTS)
    })
    public ResponseEntity<AccountDto> create(
            @Parameter(description = "Данные учетной записи", required = true)
            @RequestBody AccountDto accountDto,
            @Parameter(description = "ID пользователя создающего запись", required = true)
            @RequestHeader(ApplicationConstants.HEADER_USER_ID) String userId) {

        log.info(ApplicationConstants.LOG_CREATE_ACCOUNT, accountDto.getEntityType());
        log.info("Пользователь: {}", userId);
        AccountDto createdAccount = accountService.createAccount(accountDto, userId);
        log.info(ApplicationConstants.SUCCESS_ACCOUNT_CREATED, createdAccount.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить учетную запись по ID",
            description = ApplicationConstants.SWAGGER_DESC_GET_ACCOUNT)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = ApplicationConstants.OPERATION_SUCCESS),
            @ApiResponse(responseCode = "404", description = ApplicationConstants.ACCOUNT_NOT_FOUND)
    })
    public ResponseEntity<AccountDto> getById(
            @Parameter(description = "ID учетной записи", required = true)
            @PathVariable Long id) {

        log.info(ApplicationConstants.LOG_GET_ACCOUNT, id);

        return accountService.findById(id)
                .map(account -> {
                    log.info("Учетная запись с ID: {} найдена", id);
                    return ResponseEntity.ok(account);
                })
                .orElseGet(() -> {
                    log.warn(ApplicationConstants.ERROR_ACCOUNT_NOT_FOUND, id);
                    return ResponseEntity.notFound().build();
                });
    }

    @GetMapping
    @Operation(summary = "Получить все учетные записи",
            description = ApplicationConstants.SWAGGER_DESC_GET_ALL_ACCOUNTS)
    @ApiResponse(responseCode = "200", description = ApplicationConstants.OPERATION_SUCCESS)
    public ResponseEntity<List<AccountDto>> getAll() {

        log.info("Получен запрос на получение всех учетных записей");
        List<AccountDto> accounts = accountService.findAll();
        log.info("Найдено {} учетных записей", accounts.size());

        return ResponseEntity.ok(accounts);
    }
    @PutMapping("/{id}")
    @Operation(summary = "Обновить учетную запись",
            description = ApplicationConstants.SWAGGER_DESC_UPDATE_ACCOUNT)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = ApplicationConstants.OPERATION_SUCCESS),
            @ApiResponse(responseCode = "404", description = ApplicationConstants.ACCOUNT_NOT_FOUND),
            @ApiResponse(responseCode = "400", description = ApplicationConstants.INVALID_INPUT_DATA)
    })
    public ResponseEntity<AccountDto> update(
            @Parameter(description = "ID учетной записи", required = true)
            @PathVariable Long id,
            @Parameter(description = "Обновленные данные учетной записи", required = true)
            @RequestBody AccountDto accountDto,
            @Parameter(description = "ID пользователя обновляющего запись", required = true)
            @RequestHeader(ApplicationConstants.HEADER_USER_ID) String userId) {

        log.info(ApplicationConstants.LOG_UPDATE_ACCOUNT, id);
        log.info("Пользователь: {}", userId);
        try {
            AccountDto updatedAccount = accountService.updateAccount(id, accountDto, userId);
            log.info(ApplicationConstants.SUCCESS_ACCOUNT_UPDATED, id);
            return ResponseEntity.ok(updatedAccount);
        } catch (ExceptionHandling.EntityNotFoundException e) {
            log.warn(ApplicationConstants.ERROR_ACCOUNT_NOT_FOUND, id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error(ApplicationConstants.ERROR_VALIDATION_FAILED, e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить учетную запись",
            description = ApplicationConstants.SWAGGER_DESC_DELETE_ACCOUNT)
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Учетная запись успешно удалена"),
            @ApiResponse(responseCode = "404", description = ApplicationConstants.ACCOUNT_NOT_FOUND)
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID учетной записи", required = true)
            @PathVariable Long id,
            @Parameter(description = "ID пользователя удаляющего запись", required = true)
            @RequestHeader(ApplicationConstants.HEADER_USER_ID) String userId) {
        log.info(ApplicationConstants.LOG_DELETE_ACCOUNT, id);
        log.info("Пользователь: {}", userId);
        boolean deleted = accountService.deleteAccount(id, userId);
        if (deleted) {
            log.info(ApplicationConstants.SUCCESS_ACCOUNT_DELETED, id);
            return ResponseEntity.noContent().build();
        } else {
            log.warn(ApplicationConstants.ERROR_ACCOUNT_NOT_FOUND, id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/entity-type/{entityType}")
    @Operation(summary = "Найти учетные записи по типу сущности",
            description = "Возвращает учетные записи по указанному типу сущности")
    @ApiResponse(responseCode = "200", description = ApplicationConstants.OPERATION_SUCCESS)
    public ResponseEntity<List<AccountDto>> getByEntityType(
            @Parameter(description = "Тип сущности", required = true)
            @PathVariable String entityType) {
        log.info("Поиск учетных записей по типу сущности: {}", entityType);
        List<AccountDto> accounts = accountService.findByEntityType(entityType);
        log.info("Найдено {} учетных записей типа: {}", accounts.size(), entityType);
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/created-by/{createdBy}")
    @Operation(summary = "Найти учетные записи по создателю",
            description = "Возвращает учетные записи созданные указанным пользователем")
    @ApiResponse(responseCode = "200", description = ApplicationConstants.OPERATION_SUCCESS)
    public ResponseEntity<List<AccountDto>> getByCreator(
            @Parameter(description = "Имя пользователя создателя", required = true)
            @PathVariable String createdBy) {
        log.info("Поиск учетных записей созданных пользователем: {}", createdBy);
        List<AccountDto> accounts = accountService.findByCreatedBy(createdBy);
        log.info("Найдено {} учетных записей созданных пользователем: {}", accounts.size(), createdBy);
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/operation-type/{operationType}")
    @Operation(summary = "Найти учетные записи по типу операции",
            description = "Возвращает учетные записи по указанному типу операции")
    @ApiResponse(responseCode = "200", description = ApplicationConstants.OPERATION_SUCCESS)
    public ResponseEntity<List<AccountDto>> getByOperationType(
            @Parameter(description = "Тип операции", required = true)
            @PathVariable String operationType) {
        log.info("Поиск учетных записей по типу операции: {}", operationType);
        List<AccountDto> accounts = accountService.findByOperationType(operationType);
        log.info("Найдено {} учетных записей с типом операции: {}", accounts.size(), operationType);
        return ResponseEntity.ok(accounts);
    }
}