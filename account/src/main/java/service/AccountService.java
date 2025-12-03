package service;

import dto.AccountDto;


import java.util.List;
import java.util.Optional;

public interface AccountService {
    AccountDto createAccount(AccountDto accountDto, String createdBy);
    Optional<AccountDto> findById(Long id);
    List<AccountDto> findAll();
    AccountDto updateAccount(Long id, AccountDto accountDto, String modifiedBy);
    boolean deleteAccount(Long id, String deletedBy);
    List<AccountDto> findByEntityType(String entityType);
    List<AccountDto> findByCreatedBy(String createdBy);
    List<AccountDto> findByOperationType(String operationType);
}
