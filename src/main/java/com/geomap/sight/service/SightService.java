package com.geomap.sight.service;

import com.geomap.exceptions.sight.SightAlreadyExistsException;
import com.geomap.sight.domain.Coordinates;
import com.geomap.sight.domain.SightEntity;
import com.geomap.sight.repository.SightRepository;
import com.geomap.sight.web.dto.response.SightDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SightService {

    private final SightRepository repository;

    public List<SightEntity> getOnscreenSights(List<Coordinates> coordinates) {
        List<Double> lat = coordinates.stream().map(Coordinates::getLat).toList();
        List<Double> lon = coordinates.stream().map(Coordinates::getLon).toList();
        return repository.findAllInRectangle(
            Collections.min(lat),
            Collections.max(lat),
            Collections.min(lon),
            Collections.max(lon)
        );
    }

    public void createNewSight(SightEntity sightEntity) {
        if(
            repository.existsByCordsLatAndCordsLon(
                sightEntity.getCords().getLat(),
                sightEntity.getCords().getLon()
            )
        ) throw new SightAlreadyExistsException();
        repository.save(sightEntity);
    }

}
