package controller;

import config.ApplicationConstants;
import dto.HistoryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import service.HistoryService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
//@Slf4j
@Tag(name = ApplicationConstants.API_TAG_HISTORY, description = ApplicationConstants.API_DESCRIPTION_HISTORY)
public class HistoryController {
    private static final Logger log = LoggerFactory.getLogger(HistoryController.class);
    private  HistoryService historyService;

    @PostMapping
    @Operation(summary = "Создать запись истории",
            description = "Создает новую запись истории изменений")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Запись истории успешно создана"),
            @ApiResponse(responseCode = "400", description = ApplicationConstants.INVALID_INPUT_DATA)
    })
    public ResponseEntity<HistoryDto> createHistory(
            @Parameter(description = "Данные записи истории", required = true)
            @RequestBody HistoryDto historyDto) {

        log.info("POST /api/history - Создание записи истории");
        HistoryDto createdHistory = historyService.createHistory(historyDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdHistory);
    }

    @GetMapping
    @Operation(summary = "Получить всю историю",
            description = "Возвращает всю историю изменений с сортировкой по дате")
    @ApiResponse(responseCode = "200", description = "История успешно получена")
    public ResponseEntity<List<HistoryDto>> getAllHistory() {

        log.info("GET /api/history - Получение всей истории");
        List<HistoryDto> history = historyService.getAllHistory();
        return ResponseEntity.ok(history);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить запись истории по ID",
            description = "Возвращает запись истории по указанному идентификатору")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Запись истории найдена"),
            @ApiResponse(responseCode = "404", description = ApplicationConstants.HISTORY_NOT_FOUND)
    })
    public ResponseEntity<HistoryDto> getHistoryById(
            @Parameter(description = "ID записи истории", required = true)
            @PathVariable Long id) {

        log.info("GET /api/history/{} - Получение записи истории по ID", id);
        return historyService.getHistoryById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/aggregate")
    @Operation(summary = "Получить агрегированную историю",
            description = "Возвращает агрегированные данные истории изменений из всех микросервисов")
    @ApiResponse(responseCode = "200", description = "Агрегированная история успешно получена")
    public ResponseEntity<List<HistoryDto>> getAggregatedHistory() {

        log.info("GET /api/history/aggregate - Получение агрегированной истории");
        List<HistoryDto> aggregatedHistory = historyService.getAggregatedHistory();
        log.info("Получено {} агрегированных записей истории", aggregatedHistory.size());
        return ResponseEntity.ok(aggregatedHistory);
    }

    @GetMapping("/microservice/{microserviceName}")
    @Operation(summary = "Получить историю по микросервису",
            description = "Возвращает историю изменений для указанного микросервиса")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "История микросервиса успешно получена"),
            @ApiResponse(responseCode = "400", description = "Неверное имя микросервиса")
    })
    public ResponseEntity<List<HistoryDto>> getHistoryByMicroservice(
            @Parameter(description = "Имя микросервиса", required = true,
                    examples = {
                            @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    name = "account", value = "account"),
                            @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    name = "authorization", value = "authorization"),
                            @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    name = "anti_fraud", value = "anti_fraud")
                    })
            @PathVariable String microserviceName) {

        log.info("GET /api/history/microservice/{} - Получение истории микросервиса", microserviceName);
        List<HistoryDto> history = historyService.getHistoryByMicroservice(microserviceName);
        log.info("Получено {} записей истории для микросервиса {}", history.size(), microserviceName);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/date-range")
    @Operation(summary = "Получить историю по диапазону дат",
            description = "Возвращает историю изменений за указанный период времени")
    @ApiResponse(responseCode = "200", description = "История за период успешно получена")
    public ResponseEntity<List<HistoryDto>> getHistoryByDateRange(
            @Parameter(description = "Начальная дата", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "Конечная дата", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        log.info("GET /api/history/date-range - Получение истории с {} по {}", startDate, endDate);
        List<HistoryDto> history = historyService.getHistoryByDateRange(startDate, endDate);
        log.info("Получено {} записей истории за указанный период", history.size());
        return ResponseEntity.ok(history);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить запись истории",
            description = "Обновляет данные существующей записи истории")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Запись истории успешно обновлена"),
            @ApiResponse(responseCode = "404", description = "Запись истории не найдена")
    })
    public ResponseEntity<HistoryDto> updateHistory(
            @Parameter(description = "ID записи истории", required = true)
            @PathVariable Long id,
            @Parameter(description = "Обновленные данные записи истории", required = true)
            @RequestBody HistoryDto historyDto) {

        log.info("PUT /api/history/{} - Обновление записи истории", id);
        try {
            HistoryDto updatedHistory = historyService.updateHistory(id, historyDto);
            return ResponseEntity.ok(updatedHistory);
        } catch (ExceptionHandling.EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить запись истории",
            description = "Удаляет запись истории по указанному идентификатору")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Запись истории успешно удалена"),
            @ApiResponse(responseCode = "404", description = "Запись истории не найдена")
    })
    public ResponseEntity<Void> deleteHistory(
            @Parameter(description = "ID записи истории", required = true)
            @PathVariable Long id) {

        log.info("DELETE /api/history/{} - Удаление записи истории", id);
        try {
            historyService.deleteHistory(id);
            return ResponseEntity.noContent().build();
        } catch (ExceptionHandling.EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
