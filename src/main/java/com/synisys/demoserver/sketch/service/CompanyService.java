package com.synisys.demoserver.sketch.service;

import java.time.Duration;

import org.springframework.stereotype.Service;

import com.synisys.demoserver.sketch.model.CompanyData;

import reactor.core.publisher.Flux;

/**
 * @author Artush on 11/3/2017.
 */
@Service
public class CompanyService {
	public Flux<CompanyData> getCompanies() {
		return Flux.range(0, 10)
				.map(Long::valueOf)
				.map(id -> new CompanyData(id, "name" + id, id + 880000000))
				.delayElements(Duration.ofMillis(100));
	}
}
