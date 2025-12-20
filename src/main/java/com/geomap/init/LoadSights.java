package com.geomap.init;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geomap.sight.domain.SightEntity;
import com.geomap.sight.mapper.SightMapper;
import com.geomap.sight.repository.SightRepository;
import com.geomap.sight.web.dto.request.CreateSightRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoadSights implements CommandLineRunner {

    private final SightRepository repository;
    private final SightMapper mapper;
    private final ObjectMapper objectMapper;

    @Override
    public void run(String... args) throws Exception {
        if (repository.count() > 0) {
            log.info("All data already defined");
            return;
        }

        for(int i = 1; i <= 2; i++) {
            InputStream inputStream = new ClassPathResource("data_" + i + ".json").getInputStream();

            List<CreateSightRequest> items = objectMapper.readValue(
                    inputStream,
                    new TypeReference<>() {}
            );

            List<SightEntity> entities = items.stream().map(mapper::toEntity).toList();

            repository.saveAllAndFlush(entities);
        }
        log.info("All data already loaded to db");
    }
}
