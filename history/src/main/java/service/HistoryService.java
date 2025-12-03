package service;

import dto.HistoryDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface HistoryService {
    // Основные CRUD операции
    HistoryDto createHistory(HistoryDto historyDto);
    List<HistoryDto> getAllHistory();
    Optional<HistoryDto> getHistoryById(Long id);
    HistoryDto updateHistory(Long id, HistoryDto historyDto);
    void deleteHistory(Long id);

    // Агрегация данных
    List<HistoryDto> getAggregatedHistory();
    List<HistoryDto> getHistoryByMicroservice(String microserviceName);
    List<HistoryDto> getHistoryByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    // Поиск по конкретным аудит ID
    Optional<HistoryDto> findByAccountAuditId(Long accountAuditId);
    Optional<HistoryDto> findByAuthorizationAuditId(Long authorizationAuditId);
    Optional<HistoryDto> findByAntiFraudAuditId(Long antiFraudAuditId);
    Optional<HistoryDto> findByProfileAuditId(Long profileAuditId);
    Optional<HistoryDto> findByTransferAuditId(Long transferAuditId);
    Optional<HistoryDto> findByPublicBankInfoAuditId(Long publicBankInfoAuditId);
}
