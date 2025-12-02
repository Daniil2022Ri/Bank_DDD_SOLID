package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class ProfileDto {
    private Long id;
    private int phoneNumber;
    private String email;
    private String nameOnCard;
    private int inn;
    private int snils;

    private Long passportId;
    private Long actualRegistrationId;
}
