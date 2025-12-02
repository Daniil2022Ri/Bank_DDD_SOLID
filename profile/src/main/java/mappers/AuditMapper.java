package mappers;

import dto.AuditDto;
import entity.Audit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuditMapper {
    AuditDto toDto(AuditDto audit);
    Audit toEntity(AuditDto dto);
}
