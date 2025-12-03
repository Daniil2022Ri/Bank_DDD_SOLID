package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Profile")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "phone_number")
    private int phoneNumber;
    @Column(name = "email")
    @Size(max = 264)
    private String email;
    @Column(name = "name_On_Card")
    @Size(max = 370)
    private String nameOnCard;
    @Column(name = "inn")
    private int inn;
    @Column(name = "snils")
    private int snils;

    @Column(name = "passport_Id")
    private Long passportId;
    @Column(name = "actualRegistrationId")
    private Long actualRegistrationId;
}
