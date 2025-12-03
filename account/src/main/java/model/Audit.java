package model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import nonapi.io.github.classgraph.json.Id;

@Entity
@Builder
@AllArgsConstructor
@Data
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "Password ID")
    private long passwordId;
    @Column(name = "Account Number")
    private long accountNumber;
    @Column(name = "Bank Details ID")
    private long bankDetailsId;
    @Column(name = "Negative Balance")
    private boolean negativeBalance;
    @Column(name = "Profile Id")
    private long profileId;
}
