package model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;


@Entity
@Table(name = "history")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "transfer_audit_id")
    private Long transferAuditId;
    @Column(name = "profile_audit_id")
    private Long profileAuditId;
    @Column(name = "account_audit_id")
    private Long accountAuditId;
    @Column(name = "anti_fraud_audit_id")
    private Long antiFraudAuditId;
    @Column(name = "public_bank_info_audit_id")
    private Long publicBankInfoAuditId;
    @Column(name = "authorization_audit_id")
    private Long authorizationAuditId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
