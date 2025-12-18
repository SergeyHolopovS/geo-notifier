package com.geomap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class GeomapApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeomapApplication.class, args);
	}

}
