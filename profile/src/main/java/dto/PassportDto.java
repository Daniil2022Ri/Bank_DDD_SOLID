package dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.text.DateFormat;

@AllArgsConstructor
@Builder
@Data
public class PassportDto {
    @Schema(description = "ID Passport" , example = "1")
    private Long id;

    @Schema(description = "Series Passport" , example = "1234")
    private int series;
    @Schema(description = "Number Passport" , example = "567890")
    private int number;
    @Schema(description = "Passport Last Name" , example = "IVANOV")
    private String lastName;
    @Schema(description = "Passport First Name" , example = "IVAN")
    private String firstName;
    @Schema(description = "Passport Middle Name " , example = "IVANOVICH")
    private String middleName;
    @Schema(description = "Passport Gender" , example = "MAN")
    private String gender;
    @Schema(description = "Passport Date" , example = "24/09/2000")
    private DateFormat date;
    @Schema(description = "Passport Birth Place" , example = "town Moscow")
    private String birthPlace;
    @Schema(description = "Passport Issued" , example = "OYFMS Russian in Moscow town")
    private String issuedBy;
    @Schema(description = "Passport date Issue" , example = "20/05/2010")
    private DateFormat dateOfIssue;
    @Schema(description = "Passport Code Division" , example = "770001")
    private int divisionCode;
    @Schema(description = "Passport expiration Date" , example = "20/05/2030")
    private DateFormat expirationDate;

    private Long registrationId;
}
