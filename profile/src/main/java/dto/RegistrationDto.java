package dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class RegistrationDto {
    private Long id;

    @Schema(description = "Registration Country", example = "Russian")
    private String country;
    @Schema(description = "Registration Region", example = "Moscow area")
    private String region;
    @Schema(description = "Registration City", example = "Moscow")
    private String city;
    @Schema(description = "Registration District", example = "Middle")
    private String district;
    @Schema(description = "Registration Locality", example = "town Moscow")
    private String locality;
    @Schema(description = "Registration street", example = "Pushkinskaya street")
    private String street;
    @Schema(description = "Registration House Number", example = "10")
    private String houseNumber;
    @Schema(description = "Registration House Block", example = "1")
    private String houseBlock;
    @Schema(description = "Registration Flat Number", example = "125009")
    private String flatNumber;

    private int index;
}
