package service;

import dto.AuditDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import mappers.AuditMapper;
import model.Account;
import model.Audit;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repositories.AuditRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class AuditServiceImpl implements AuditService {

    private final AuditRepository auditRepository;
    private final AuditMapper auditMapper;

    @Override
    public void logAccountCreation(Account account, String createdBy) {
        try {
            Audit audit = Audit.builder()
                    .passwordId(0L)
                    .accountNumber(0L)
                    .bankDetailsId(0L)
                    .negativeBalance(false)
                    .profileId(0L)
                    .build();

            auditRepository.save(audit);
            log.debug("Создан журнал аудита для создания учетной записи: {}", account.getId());
        } catch (Exception e) {
            log.error("Не удалось создать журнал аудита для создания учетной записи", e);
        }
    }

    @Override
    public void logAccountUpdate(Account oldAccount, Account newAccount, String modifiedBy) {
        try {
            Audit audit = Audit.builder()
                    .passwordId(0L)
                    .accountNumber(0L)
                    .bankDetailsId(0L)
                    .negativeBalance(false)
                    .profileId(0L)
                    .build();

            auditRepository.save(audit);
            log.debug("Создан журнал аудита для обновления учетной записи: {}", newAccount.getId());
        } catch (Exception e) {
            log.error("Не удалось создать журнал аудита для обновления учетной записи", e);
        }
    }

    @Override
    public void logAccountDeletion(Account account, String deletedBy) {
        try {

            Audit audit = Audit.builder()
                    .passwordId(0L)
                    .accountNumber(0L)
                    .bankDetailsId(0L)
                    .negativeBalance(false)
                    .profileId(0L)
                    .build();

            auditRepository.save(audit);
            log.debug("Создан журнал аудита для удаления учетной записи: {}", account.getId());
        } catch (Exception e) {
            log.error("Не удалось создать журнал аудита для удаления учетной записи", e);
        }
    }

    @Override
    public AuditDto createAudit(AuditDto auditDto) {
        log.info("Создание новой записи аудита");
        Audit audit = auditMapper.toEntity(auditDto);
        Audit savedAudit = auditRepository.save(audit);
        return auditMapper.toDto(savedAudit);
    }

    @Override
    public Optional<AuditDto> findAuditById(Long id) {
        log.debug("Поиск аудита по идентификатору ID: {}", id);
        return auditRepository.findById(id)
                .map(auditMapper::toDto);
    }

    @Override
    public List<AuditDto> findAllAudits() {
        log.debug("Поиск всех записей аудита");
        return auditRepository.findAll().stream()
                .map(auditMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AuditDto> findByPasswordId(Long passwordId) {
        log.debug("Поиск аудитов по паролю ID: {}", passwordId);
        return auditRepository.findByPasswordId(passwordId).stream()
                .map(auditMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AuditDto> findByAccountNumber(Long accountNumber) {
        log.debug("Поиск аудитов по номеру счета: {}", accountNumber);
        return auditRepository.findByAccountNumber(accountNumber).stream()
                .map(auditMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AuditDto> findByProfileId(Long profileId) {
        log.debug("Поиск аудитов по идентификатору профиля: {}", profileId);
        return auditRepository.findByProfileId(profileId).stream()
                .map(auditMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AuditDto> findByNegativeBalance(boolean negativeBalance) {
        log.debug("Поиск аудитов по отрицательному балансу: {}", negativeBalance);
        return auditRepository.findByNegativeBalance(negativeBalance).stream()
                .map(auditMapper::toDto)
                .collect(Collectors.toList());
    }
}
