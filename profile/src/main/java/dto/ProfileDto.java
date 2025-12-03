package dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.intellij.lang.annotations.Pattern;

@Schema(description = "DTO Profile")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProfileDto {
    @Schema(description = "ID Profile")
    private Long id;
    @Schema(description = "Number Phone" , example = "88005553535")
    private int phoneNumber;
    @Schema(description = "Profile Email" , example = "user@mail.ru")
    private String email;
    @Schema(description = "Profile Name Card" , example = "User User")
    private String nameOnCard;
    @Schema(description = "INN" , example = "123456789012")
    private int inn;
    @Schema(description = "SNILS", example = "12345678901")
    private int snils;

    private Long passportId;
    private Long actualRegistrationId;
}
