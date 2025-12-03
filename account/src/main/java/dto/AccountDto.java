package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@Builder
@Data
public class AccountDto {

    private Long id;

    private String entityType;
    private String operationType;
    private String createdBy;
    private String modifiedBy;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String newEntityJson;
    private String entityJson;

}
