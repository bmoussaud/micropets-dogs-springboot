package org.moussaud.micropets.pets;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class DogController {

	private static Logger logger = LoggerFactory.getLogger(DogController.class);

	private final Supplier<Long> latency = () -> new Random().nextLong(500);

	@Autowired
	DogRepository repository;

	@Autowired
	DogGenerator generator;

	@Autowired
	DogSummary summary;

	@GetMapping(value = "/liveness")
	public String liveness() {
		logger.debug("liveness");
		return "okay";
	}

	@GetMapping(value = "/readiness")
	public String readiness() {
		logger.debug("readiness");
		return "okay";
	}

	@GetMapping(value = { "", "/", "/dogs/v1/data" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public DogSummary Dogs() {

		logger.debug("search all Dogs");
		summary.clear();
		try {
			if (repository.count() == 0) {
				return this.load();
			} else {
				for (DogBean Dog : repository.findAll()) {
					summary.addPet(Dog);
				}
			}
		} catch (Exception e) {
			return this.load();
		}
		waitABit();
		return summary.filter();
	}

	private void waitABit() {
		try {
			long l = latency.get().longValue();
			logger.debug("wait a bit " + l + "ms");
			Thread.sleep(l);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@GetMapping(value = "/dogs/v1/data/{index}", produces = MediaType.APPLICATION_JSON_VALUE)
	public DogBean Dog(@PathVariable Long index) {
		logger.debug("get Dog by Index " + index);
		summary.clear();
		DogBean dog = repository.findById(index).orElse(new DogBean());
		return summary.upgrade(dog);
	}

	@GetMapping(value = "/dogs/v1/load", produces = MediaType.APPLICATION_JSON_VALUE)
	public DogSummary load() {
		logger.debug("load values in the Dog database");
		DogSummary summary = generator.generate();
		logger.debug("Save All : Insert in db:" + summary.pets);
		repository.saveAll(summary.pets);
		return summary;

	}

}