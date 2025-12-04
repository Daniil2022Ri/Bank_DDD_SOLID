package dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NotNull
@Valid
@Size
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class SuspiciousCardTransferDto {

    private Long id;
    private boolean blocked;
    private boolean suspicious;
    private String blockedReason;
    private String suspiciousReason;

}