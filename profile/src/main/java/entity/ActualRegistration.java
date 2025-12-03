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

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "actual_registration")
public class ActualRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "country")
    @Size(max = 40)
    private String country;
    @Column(name = "region")
    @Size(max = 160)
    private String region;
    @Column(name = "city")
    @Size(max = 160)
    private String city;
    @Column(name = "district")
    @Size(max = 160)
    private String district;
    @Column(name = "locality")
    @Size(max = 230)
    private String locality;
    @Column(name = "street")
    @Size(max = 230)
    private String street;
    @Column(name = "house_number")
    @Size(max = 20)
    private String houseNumber;
    @Column(name = "house_block")
    @Size(max = 20)
    private String houseBlock;
    @Column(name = "flat_number")
    @Size(max = 40)
    private String flatNumber;
    @Column(name = "index")
    private int index;

}
