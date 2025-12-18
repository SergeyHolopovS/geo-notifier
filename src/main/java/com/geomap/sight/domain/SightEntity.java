package com.geomap.sight.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SightEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String objectName;

    private Integer registryNumber;

    private String fullAddress;

    @Embedded
    private Coordinates cords;

    private String regionName;

    private String category;

    private String type;

    private String description;

    private Integer creationYear;

    private String imageUrl;

}
