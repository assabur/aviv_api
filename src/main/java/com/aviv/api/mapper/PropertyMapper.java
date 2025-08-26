package com.aviv.api.mapper;

import com.aviv.api.entity.Property;
import com.aviv.api.dto.PropertyDto;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PropertyMapper {
    // ----- Entity -> DTO
    PropertyDto toDto(Property entity);

}
