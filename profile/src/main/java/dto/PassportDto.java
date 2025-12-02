package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.text.DateFormat;

@AllArgsConstructor
@Builder
@Data
public class PassportDto {
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
