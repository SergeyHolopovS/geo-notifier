package com.geomap.utils;

import com.redis.testcontainers.RedisContainer;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public interface TestContainersConfig {

    PostgreSQLContainer POSTGRES = new PostgreSQLContainer(DockerImageName.parse("postgres:16.3-alpine"))
            .withDatabaseName("admin")
            .withUsername("admin")
            .withPassword("admin")
            .withExposedPorts(5432);

    RedisContainer REDIS = new RedisContainer(DockerImageName.parse("redis:5"))
            .withExposedPorts(6379);

}