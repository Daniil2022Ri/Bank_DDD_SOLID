package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;

@AllArgsConstructor
@Builder
@Data
public class AuditDto {
    private Long id;

    private String entityType;
    private String operationType;
    private String createdBy;
    private String modifiedBy;
    private LocalTime createdAt;
    private LocalTime modifiedAt;
    private String newEntityJson;
    private String entityJson;
}
