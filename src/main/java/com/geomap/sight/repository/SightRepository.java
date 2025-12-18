package com.geomap.sight.repository;

import com.geomap.sight.domain.SightEntity;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@CacheConfig(cacheNames = "sightRepository")
public interface SightRepository extends JpaRepository<SightEntity, UUID> {

    @Query("""
        SELECT s FROM SightEntity s
        WHERE s.cords.lat BETWEEN :minLat AND :maxLat
          AND s.cords.lon BETWEEN :minLon AND :maxLon
    """)
    List<SightEntity> findAllInRectangle(
            @Param("minLat") Double minLat,
            @Param("maxLat") Double maxLat,
            @Param("minLon") Double minLon,
            @Param("maxLon") Double maxLon
    );

    boolean existsByCordsLatAndCordsLon(Double lat, Double lon);

}
