package com.geomap.sight.mapper;

import com.geomap.sight.domain.SightEntity;
import com.geomap.sight.web.dto.request.CreateSightRequest;
import com.geomap.sight.web.dto.response.SightDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface SightMapper {


    // Request → Entity
    @Mapping(target = "id", ignore = true)
    SightEntity toEntity(CreateSightRequest request);

    // Entity → Response DTO
    SightDto toDto(SightEntity entity);

}
