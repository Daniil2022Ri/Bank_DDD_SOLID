package mappers;

import dto.ProfileDto;
import entity.ProfileEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileDto toDto(ProfileDto profile);
    ProfileEntity toEntity(ProfileDto dto);
}
