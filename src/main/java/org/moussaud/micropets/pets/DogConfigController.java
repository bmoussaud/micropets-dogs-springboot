package org.moussaud.micropets.pets;

import org.springframework.web.bind.annotation.RestController;

import com.zaxxer.hikari.HikariDataSource;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class DogConfigController {

	private static Logger logger = LoggerFactory.getLogger(DogConfigController.class);

	private final HikariDataSource dataSource;

	public DogConfigController(HikariDataSource dataSource) {
		this.dataSource = dataSource;
	}

	@GetMapping(value = "/dogs/v1/config", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, ?> config() {

		logger.debug("configuration the Dog database");
		return Map.of(
				"datasource.url", dataSource.getJdbcUrl(),
				"datasource.driver", dataSource.getDriverClassName(),
				"kind", "dogs",
				"hostname", getHostname());

	}

	private String getHostname() {
		try {
			return InetAddress.getLocalHost().toString();
		} catch (UnknownHostException e) {
			return "Unknown";
		}
	}

}