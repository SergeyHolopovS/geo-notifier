package com.geomap.sight.web.dto.response;

import com.geomap.sight.domain.Coordinates;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SightDto {
    private UUID id;
    private String objectName;
    private String registryNumber;
    private String fullAddress;
    private Coordinates cords;
    private String regionName;
    private String category;
    private String type;
    private String description;
    private String creationYear;
    private String imageUrl;
}
