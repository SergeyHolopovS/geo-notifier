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

    @Column(length = 1024)
    private String objectName;

    @Column(length = 512)
    private String registryNumber;

    @Column(length = 2048)
    private String fullAddress;

    @Embedded
    private Coordinates cords;

    @Column(length = 512)
    private String regionName;

    @Column(length = 512)
    private String category;

    @Column(length = 512)
    private String type;

    @Column(length = 524288)
    private String description;

    @Column(length = 512)
    private String creationYear;

    @Column(length = 4096)
    private String imageUrl;

}
