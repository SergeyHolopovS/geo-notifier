package com.geomap.sights;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.geomap.GeomapApplication;
import com.geomap.sight.domain.Coordinates;
import com.geomap.sight.domain.SightEntity;
import com.geomap.sight.repository.SightRepository;
import com.geomap.sight.web.dto.request.CreateSightRequest;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.postgresql.PostgreSQLContainer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(DBUtils.class)
@DisplayName("Тесты создания памятника")
@FieldDefaults(level = AccessLevel.PRIVATE)
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@SpringBootTest(classes = {GeomapApplication.class})
public class CreateSightTest implements TestContainersConfig {

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
        CreateSightRequest request = CreateSightRequest
                .builder()
                .secret("1234567890")
                .objectName("Some name")
                .registryNumber(123456)
                .fullAddress("52 Lotsmanskay street St.Petersburg")
                .cords(
                    new Coordinates(52.0, 52.0)
                )
                .regionName("St.Petersburd")
                .category("Достопримечательность")
                .type("Памятник")
                .description("Very interesting description")
                .creationYear(2025)
                .imageUrl("https://i.pinimg.com/736x/16/1e/3d/161e3d6e60b5e42ebca673b941eba662.jpg")
                .build();
        mvc.perform(
            post(DEFAULT_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    mapper.writeValueAsString(request)
                )
        ).andExpect(
            status().isNoContent()
        );

        List<SightEntity> entityList = repository.findAllInRectangle(
            52.0,
            52.0,
            52.0,
            52.0
        );

        assertEquals(1, entityList.size());

        SightEntity entity = entityList.get(0);

        assertAll(
            () -> assertEquals(request.objectName(), entity.getObjectName()),
            () -> assertEquals(request.registryNumber(), entity.getRegistryNumber()),
            () -> assertEquals(request.fullAddress(), entity.getFullAddress()),
            () -> assertEquals(request.cords(), entity.getCords()),
            () -> assertEquals(request.regionName(), entity.getRegionName()),
            () -> assertEquals(request.category(), entity.getCategory()),
            () -> assertEquals(request.type(), entity.getType()),
            () -> assertEquals(request.creationYear(), entity.getCreationYear()),
            () -> assertEquals(request.imageUrl(), entity.getImageUrl())
        );
    }

    @Test
    @DisplayName("Неправильный ключ")
    void failure_bad_secret() throws Exception {
        CreateSightRequest request = CreateSightRequest
                .builder()
                .secret("123")
                .objectName("Some name")
                .registryNumber(123456)
                .fullAddress("52 Lotsmanskay street St.Petersburg")
                .cords(
                    new Coordinates(52.0, 52.0)
                )
                .regionName("St.Petersburd")
                .category("Достопримечательность")
                .type("Памятник")
                .description("Very interesting description")
                .creationYear(2025)
                .imageUrl("https://i.pinimg.com/736x/16/1e/3d/161e3d6e60b5e42ebca673b941eba662.jpg")
                .build();
        mvc.perform(
            post(DEFAULT_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    mapper.writeValueAsString(request)
                )
        ).andExpect(
            status().isForbidden()
        );
    }

    @Test
    @DisplayName("Нет ключа")
    void failure_no_secret() throws Exception {
        CreateSightRequest request = CreateSightRequest
                .builder()
                .objectName("Some name")
                .registryNumber(123456)
                .fullAddress("52 Lotsmanskay street St.Petersburg")
                .cords(
                    new Coordinates(52.0, 52.0)
                )
                .regionName("St.Petersburd")
                .category("Достопримечательность")
                .type("Памятник")
                .description("Very interesting description")
                .creationYear(2025)
                .imageUrl("https://i.pinimg.com/736x/16/1e/3d/161e3d6e60b5e42ebca673b941eba662.jpg")
                .build();
        mvc.perform(
            post(DEFAULT_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    mapper.writeValueAsString(request)
                )
        ).andExpect(
            status().isBadRequest()
        );
    }

}
