package mappers;

import dto.AuditDto;
import model.Audit;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AuditMapper {
    AuditMapper INSTANCE = Mappers.getMapper(AuditMapper.class);
    AuditDto toDto(Audit audit);
    Audit toEntity(AuditDto dto);
}
