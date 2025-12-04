package dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@NotNull
@Valid
@Size
@AllArgsConstructor
public class SuspiciousPhoneTransferDto {

    private Long id;
    private boolean blocked;
    private boolean suspicious;
    private String blockedReason;
    private String suspiciousReason;

}
