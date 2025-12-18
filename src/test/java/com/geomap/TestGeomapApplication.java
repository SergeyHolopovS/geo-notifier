package com.geomap;

import org.springframework.boot.SpringApplication;

public class TestGeomapApplication {

	public static void main(String[] args) {
		SpringApplication.from(GeomapApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
