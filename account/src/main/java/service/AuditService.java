package service;

import dto.AuditDto;
import model.Account;
import java.util.List;
import java.util.Optional;

 public interface AuditService {
    void logAccountCreation(Account account, String createdBy);
    void logAccountUpdate(Account oldAccount, Account newAccount, String modifiedBy);
    void logAccountDeletion(Account account, String deletedBy);


    AuditDto createAudit(AuditDto auditDto);
    Optional<AuditDto> findAuditById(Long id);
    List<AuditDto> findAllAudits();
    List<AuditDto> findByPasswordId(Long passwordId);
    List<AuditDto> findByAccountNumber(Long accountNumber);
    List<AuditDto> findByProfileId(Long profileId);
    List<AuditDto> findByNegativeBalance(boolean negativeBalance);
}
