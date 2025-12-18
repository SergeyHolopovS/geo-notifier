package com.geomap.sight.web;

import com.geomap.exceptions.security.AdminTokenInvalidException;
import com.geomap.sight.domain.SightEntity;
import com.geomap.sight.mapper.SightMapper;
import com.geomap.sight.service.SightService;
import com.geomap.sight.web.dto.request.CreateSightRequest;
import com.geomap.sight.web.dto.request.FindSightsRequest;
import com.geomap.sight.web.dto.response.SightDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sights")
@RequiredArgsConstructor
public class SightController {

    @Value("${security.secret}")
    private String secret;
    private final SightService service;
    private final SightMapper mapper;

    @GetMapping
    public ResponseEntity<List<SightDto>> findSights(@ModelAttribute @Valid FindSightsRequest request) {
        List<SightEntity> sights = service.getOnscreenSights(
            List.of(
                request.angle1(),
                request.angle2(),
                request.angle3(),
                request.angle4()
            )
        );
        return ResponseEntity.ok(
            sights.stream().map(mapper::toDto).toList()
        );
    }

    @PostMapping
    public ResponseEntity<Void> createSight(@RequestBody @Valid CreateSightRequest request) {
        if(!request.secret().equals(secret)) throw new AdminTokenInvalidException();
        service.createNewSight(
            mapper.toEntity(request)
        );
        return ResponseEntity.noContent().build();
    }

}
