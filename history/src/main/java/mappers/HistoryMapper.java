package mappers;

import dto.HistoryDto;
import model.History;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface HistoryMapper {
    HistoryMapper INSTANCE = Mappers.getMapper(HistoryMapper.class);

    HistoryDto toDto(History history);
    History toEntity(HistoryDto historyDto);
}
