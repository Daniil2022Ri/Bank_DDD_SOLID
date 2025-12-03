package entity;

import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.Size;
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

    @Column(name = "series")
    private int series;
    @Column(name = "number")
    private int number;
    @Column(name = "last_name")
    @Size(max = 255)
    private String lastName;
    @Column(name = "first_name")
    @Size(max = 255)
    private String firstName;
    @Column(name = "middle_name")
    @Size(max = 255)
    private String middleName;
    @Column(name = "gender")
    @Size(min = 3 , max = 3)
    private String gender;
    @Column(name = "date")
    private DateFormat date;
    @Column(name = "birth_place")
    @Size(max = 480)
    private String birthPlace;
    @Column(name = "issued_by")
    private String issuedBy;
    @Column(name = "date_Of_Issue")
    private DateFormat dateOfIssue;
    @Column(name = "division_code")
    private int divisionCode;
    @Column(name = "expiration_date")
    private DateFormat expirationDate;

    private Long registrationId;
}
