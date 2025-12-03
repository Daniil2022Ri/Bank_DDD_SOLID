package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class AuditDto {

    private Long id;
    private long passwordId;
    private long accountNumber;
    private long bankDetailsId;
    private boolean negativeBalance;
    private long profileId;
}
