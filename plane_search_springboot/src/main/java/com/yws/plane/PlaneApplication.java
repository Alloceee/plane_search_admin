package com.yws.plane;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author AlmostLover
 */
@SpringBootApplication
@RestController
public class PlaneApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlaneApplication.class, args);
	}

}
