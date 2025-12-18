package com.geomap.sights;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.geomap.GeomapApplication;
import com.geomap.sight.repository.SightRepository;
import com.geomap.sight.web.dto.response.SightDto;
import com.geomap.utils.DBUtils;
import com.geomap.utils.TestContainersConfig;
import com.redis.testcontainers.RedisContainer;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.postgresql.PostgreSQLContainer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(DBUtils.class)
@DisplayName("Тесты поиска памятников")
@FieldDefaults(level = AccessLevel.PRIVATE)
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@SpringBootTest(classes = {GeomapApplication.class})
public class FindSightsTests implements TestContainersConfig {

    @Container
    @ServiceConnection
    private static PostgreSQLContainer postgres = POSTGRES;

    @Container
    @ServiceConnection
    private static RedisContainer redis = REDIS;

    @Autowired
    MockMvc mvc;

    final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    final static String DEFAULT_URL = "/api/sights";

    @Autowired
    DBUtils dbUtils;

    @Autowired
    SightRepository repository;

    @BeforeEach
    void init() {
        repository.deleteAll();

        repository.saveAll(
            dbUtils.sightsFactory(10)
        );
    }

    @Test
    @DisplayName("Успех")
    void success() throws Exception {
        String result = mvc.perform(
            get(DEFAULT_URL)
                .param("angle1.lat", "4.0")
                .param("angle1.lon", "4.0")
                .param("angle2.lat", "-4.0")
                .param("angle2.lon", "-4.0")
                .param("angle3.lat", "-4.0")
                .param("angle3.lon", "4.0")
                .param("angle4.lat", "4.0")
                .param("angle4.lon", "-4.0")
        ).andExpect(
            status().isOk()
        )
            .andReturn()
            .getResponse()
            .getContentAsString();

        List<SightDto> response = mapper.readValue(result, new TypeReference<>() {});

        assertEquals(5, response.size());
    }

    @Test
    @DisplayName("Пустой список")
    void success_empty() throws Exception {
        String result = mvc.perform(
            get(DEFAULT_URL)
                .param("angle1.lat", "0.4")
                .param("angle1.lon", "0.4")
                .param("angle2.lat", "0.3")
                .param("angle2.lon", "0.3")
                .param("angle3.lat", "0.4")
                .param("angle3.lon", "0.3")
                .param("angle4.lat", "0.3")
                .param("angle4.lon", "0.4")
            ).andExpect(
                status().isOk()
            )
            .andReturn()
            .getResponse()
            .getContentAsString();

        List<SightDto> response = mapper.readValue(result, new TypeReference<>() {});

        assertEquals(0, response.size());
    }

    @Test
    @DisplayName("Без параметров")
    void failure_no_args() throws Exception {
        mvc.perform(
                get(DEFAULT_URL)
            ).andExpect(
                status().isBadRequest()
            );
    }

}
