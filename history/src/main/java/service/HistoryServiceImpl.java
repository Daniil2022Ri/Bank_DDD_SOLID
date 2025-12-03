package service;

import ExceptionHandling.EntityNotFoundException;
import ExceptionHandling.ValidationException;
import controller.HistoryController;
import dto.HistoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mappers.HistoryMapper;
import model.History;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.HistoryRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class HistoryServiceImpl implements HistoryService {
    //private static final Logger log = LoggerFactory.getLogger(HistoryServiceImpl.class);
    private  HistoryRepository historyRepository;
    private  HistoryMapper historyMapper;

    @Override
    public HistoryDto createHistory(HistoryDto historyDto) {
        log.info("Создание новой записи истории");
        History history = historyMapper.toEntity(historyDto);
        History savedHistory = historyRepository.save(history);
        log.info("Запись истории создана с ID: {}", savedHistory.getId());
        return historyMapper.toDto(savedHistory);
    }

    @Override
    public List<HistoryDto> getAllHistory() {
       log.info("Получение всей истории изменений");
        return historyRepository.findAllOrderByCreatedAtDesc().stream()
                .map(historyMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<HistoryDto> getHistoryById(Long id) {
       log.info("Поиск записи истории по ID: {}", id);
        return historyRepository.findById(id)
                .map(historyMapper::toDto);
    }

    @Override
    public HistoryDto updateHistory(Long id, HistoryDto historyDto) {
       log.info("Обновление записи истории с ID: {}", id);

        History existingHistory = historyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Запись истории не найдена с ID: " + id));

        // Обновляем поля
        if (historyDto.getTransferAuditId() != null) {
            existingHistory.setTransferAuditId(historyDto.getTransferAuditId());
        }
        if (historyDto.getProfileAuditId() != null) {
            existingHistory.setProfileAuditId(historyDto.getProfileAuditId());
        }
        if (historyDto.getAccountAuditId() != null) {
            existingHistory.setAccountAuditId(historyDto.getAccountAuditId());
        }
        if (historyDto.getAntiFraudAuditId() != null) {
            existingHistory.setAntiFraudAuditId(historyDto.getAntiFraudAuditId());
        }
        if (historyDto.getPublicBankInfoAuditId() != null) {
            existingHistory.setPublicBankInfoAuditId(historyDto.getPublicBankInfoAuditId());
        }
        if (historyDto.getAuthorizationAuditId() != null) {
            existingHistory.setAuthorizationAuditId(historyDto.getAuthorizationAuditId());
        }

        History updatedHistory = historyRepository.save(existingHistory);
       log.info("Запись истории с ID: {} успешно обновлена", id);

        return historyMapper.toDto(updatedHistory);
    }

    @Override
    public void deleteHistory(Long id) {
       log.info("Удаление записи истории с ID: {}", id);
        if (historyRepository.existsById(id)) {
            historyRepository.deleteById(id);
        log.info("Запись истории с ID: {} успешно удалена", id);
        } else {
            throw new EntityNotFoundException("Запись истории не найдена с ID: " + id);
        }
    }

    @Override
    public List<HistoryDto> getAggregatedHistory() {
        log.info("Получение агрегированной истории из всех микросервисов");
        return getAllHistory(); // В реальном сценарии здесь может быть сложная агрегация
    }

    @Override
    public List<HistoryDto> getHistoryByMicroservice(String microserviceName) {
        log.info("Получение истории для микросервиса: {}", microserviceName);

        List<History> histories = switch (microserviceName.toLowerCase()) {
            case "account" -> historyRepository.findByAccountAuditIdIsNotNull();
            case "authorization" -> historyRepository.findByAuthorizationAuditIdIsNotNull();
            case "anti_fraud", "antifraud" -> historyRepository.findByAntiFraudAuditIdIsNotNull();
            case "profile" -> historyRepository.findByProfileAuditIdIsNotNull();
            case "transfer" -> historyRepository.findByTransferAuditIdIsNotNull();
            case "public_bank_info", "publicbankinfo" -> historyRepository.findByPublicBankInfoAuditIdIsNotNull();
            default -> throw new ValidationException("Неизвестный микросервис: " + microserviceName);
        };

        return histories.stream()
                .map(historyMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<HistoryDto> getHistoryByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        log.info("Получение истории за период с {} по {}", startDate, endDate);
        return historyRepository.findByCreatedAtBetween(startDate, endDate).stream()
                .map(historyMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<HistoryDto> findByAccountAuditId(Long accountAuditId) {
        log.debug("Поиск истории по accountAuditId: {}", accountAuditId);
        return historyRepository.findByAccountAuditId(accountAuditId)
                .map(historyMapper::toDto);
    }

    @Override
    public Optional<HistoryDto> findByAuthorizationAuditId(Long authorizationAuditId) {
        log.debug("Поиск истории по authorizationAuditId: {}", authorizationAuditId);
        return historyRepository.findByAuthorizationAuditId(authorizationAuditId)
                .map(historyMapper::toDto);
    }

    @Override
    public Optional<HistoryDto> findByAntiFraudAuditId(Long antiFraudAuditId) {
        log.debug("Поиск истории по antiFraudAuditId: {}", antiFraudAuditId);
        return historyRepository.findByAntiFraudAuditId(antiFraudAuditId)
                .map(historyMapper::toDto);
    }

    @Override
    public Optional<HistoryDto> findByProfileAuditId(Long profileAuditId) {
        log.debug("Поиск истории по profileAuditId: {}", profileAuditId);
        return historyRepository.findByProfileAuditId(profileAuditId)
                .map(historyMapper::toDto);
    }

    @Override
    public Optional<HistoryDto> findByTransferAuditId(Long transferAuditId) {
        log.debug("Поиск истории по transferAuditId: {}", transferAuditId);
        return historyRepository.findByTransferAuditId(transferAuditId)
                .map(historyMapper::toDto);
    }

    @Override
    public Optional<HistoryDto> findByPublicBankInfoAuditId(Long publicBankInfoAuditId) {
        log.debug("Поиск истории по publicBankInfoAuditId: {}", publicBankInfoAuditId);
        return historyRepository.findByPublicBankInfoAuditId(publicBankInfoAuditId)
                .map(historyMapper::toDto);
    }
}
