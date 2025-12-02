package entity;

import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.text.DateFormat;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "passport")
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private int series;
    private int number;
    private String lastName;
    private String firstName;
    private String middleName;
    private String gender;
    private DateFormat date;
    private String birthPlace;
    private String issuedBy;
    private DateFormat dateOfIssue;
    private int divisionCode;
    private DateFormat expirationDate;

    private Long registrationId;
}
