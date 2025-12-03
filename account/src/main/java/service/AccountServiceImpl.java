package service;

import ExceptionHandling.EntityNotFoundException;
import dto.AccountDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mappers.AccountMapper;
import model.Account;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repositories.AccountRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService{

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final AuditService auditService;
    private final AccountDto accountDto;


    @Override
    public AccountDto createAccount(AccountDto accountDto, String createdBy) {
        log.info("Создание новой учетной записи для организации: {}", accountDto.getEntityType());

        Account account = accountMapper.toEntity(accountDto);
        account.setCreatedBy(createdBy);
        account.setModifiedBy(createdBy);

        Account savedAccount = accountRepository.save(account);

        auditService.logAccountCreation(savedAccount, createdBy);
        log.info("Запись учетной записи успешно создана ID: {}", savedAccount.getId());

        return accountMapper.toDto(savedAccount);
    }

    @Override
    public Optional<AccountDto> findById(Long id) {
        log.debug("Поиск аккаунта по ID: {}", id);
        return accountRepository.findById(id)
                .map(accountMapper::toDto);
    }

    @Override
    public List<AccountDto> findAll() {
        log.debug("Поиск всех записей учетной записи");
        return accountRepository.findAll().stream()
                .map(accountMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDto updateAccount(Long id, AccountDto accountDto, String modifiedBy) {
        log.info("Обновление аккаунта с помощью ID: {}", id);

        Account existingAccount = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Учетная запись с идентификатором не найдена id: " + id));

        Account oldAccount = copyAccount(existingAccount);

        if (accountDto.getEntityType() != null) {
            existingAccount.setEntityType(accountDto.getEntityType());
        }
        if (accountDto.getOperationType() != null) {
            existingAccount.setOperationType(accountDto.getOperationType());
        }
        if (accountDto.getNewEntityJson() != null) {
            existingAccount.setNewEntityJson(accountDto.getNewEntityJson());
        }
        if (accountDto.getEntityJson() != null) {
            existingAccount.setEntityJson(accountDto.getEntityJson());
        }

        existingAccount.setModifiedBy(modifiedBy);

        Account updatedAccount = accountRepository.save(existingAccount);

        auditService.logAccountUpdate(oldAccount, updatedAccount, modifiedBy);
        log.info("Аккаунт успешно обновлен с помощью ID: {}", id);

        return accountMapper.toDto(updatedAccount);
    }

    @Override
    public boolean deleteAccount(Long id, String deletedBy) {
        log.info("Удаление аккаунта ID: {}", id);

        return accountRepository.findById(id)
                .map(account -> {
                    // Логируем удаление в аудит
                    auditService.logAccountDeletion(account, deletedBy);
                    accountRepository.delete(account);
                    log.info("Аккаунт успешно удален с помощью ID: {}", id);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public List<AccountDto> findByEntityType(String entityType) {
        log.debug("Поиск счетов по типу сущности: {}", entityType);
        return accountRepository.findByEntityType(entityType).stream()
                .map(accountMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AccountDto> findByCreatedBy(String createdBy) {
        log.debug("Поиск учетных записей, созданных: {}", createdBy);
        return accountRepository.findByCreatedBy(createdBy).stream()
                .map(accountMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AccountDto> findByOperationType(String operationType) {
        log.debug("Поиск счетов по типу операции: {}", operationType);
        return accountRepository.findByOperationType(operationType).stream()
                .map(accountMapper::toDto)
                .collect(Collectors.toList());
    }

    private Account copyAccount(Account account) {
        return Account.builder()
                .id(account.getId())
                .entityType(account.getEntityType())
                .operationType(account.getOperationType())
                .createdBy(account.getCreatedBy())
                .modifiedBy(account.getModifiedBy())
                .createdAt(account.getCreatedAt())
                .modifiedAt(account.getModifiedAt())
                .newEntityJson(account.getNewEntityJson())
                .entityJson(account.getEntityJson())
                .build();
    }
}

