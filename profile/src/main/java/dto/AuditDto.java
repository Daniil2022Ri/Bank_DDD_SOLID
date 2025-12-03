package dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;

@AllArgsConstructor
@Builder
@Data
public class AuditDto {
    @Schema(description = "ID Audit", example = "1")
    private Long id;
    @Schema(description = "Audit Entity Type", example = "Profile")
    private String entityType;
    @Schema(description = "Audit Operation Type", example = "Create")
    private String operationType;
    @Schema(description = "Audit Created", example = "admin")
    private String createdBy;
    @Schema(description = "Audit Modified", example = "user")
    private String modifiedBy;
    @Schema(description = "Audit Create AT", example = "10/10/2010")
    private LocalTime createdAt;
    @Schema(description = "Audit Modified AT", example = "10/10/2010")
    private LocalTime modifiedAt;
    @Schema(description = "Audit New Entity Json")
    private String newEntityJson;
    @Schema(description = "Audit Entity Json")
    private String entityJson;
}
