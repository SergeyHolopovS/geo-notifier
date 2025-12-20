package com.geomap.sight.web.dto.request;

import com.geomap.sight.domain.Coordinates;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CreateSightRequest (
    @NotBlank
    String secret,
    @NotBlank
    String objectName,
    @NotNull
    Integer registryNumber,
    @NotBlank
    String fullAddress,
    @NotNull
    Coordinates cords,
    @NotBlank
    String regionName,
    @NotBlank
    String category,
    @NotBlank
    String type,
    @NotBlank
    String description,
    @NotNull
    String creationYear,
    @NotBlank
    String imageUrl
) {
}
