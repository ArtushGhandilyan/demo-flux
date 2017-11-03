package com.synisys.demoserver.sketch;

import java.time.Duration;
import java.util.List;

import com.synisys.demoserver.sketch.model.CompanyInfo;
import com.synisys.demoserver.sketch.model.Data;
import com.synisys.demoserver.sketch.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

/**
 * @author Artush on 11/3/2017.
 */
@RestController
public class DemoController {

	@Autowired
	DemoService demoService;

	@GetMapping(value = "/stream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Flux<Data> stream() {
		return Flux.range(1, 1000).map(Long::valueOf).map(aLong -> new Data(aLong, aLong + "-name"));
	}

	@GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public Flux<Data> list() {
		return Flux.range(1, 1000).map(Long::valueOf).map(aLong -> new Data(aLong, aLong + "-name"));
	}

	@GetMapping(value = "/stream-delay", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Flux<Data> streamDelay() {
		return Flux.range(1, 1000).delayElements(Duration.ofMillis(10)).map(Long::valueOf).map(
				aLong -> new Data(aLong, aLong + "-name"));
	}

	@GetMapping(value = "/list-delay", produces = MediaType.APPLICATION_JSON_VALUE)
	public Flux<Data> listDelay() {
		return Flux.range(1, 1000).delayElements(Duration.ofMillis(10)).map(Long::valueOf).map(
				aLong -> new Data(aLong, aLong + "-name"));
	}

	@GetMapping(value = "/stream-delay-widow", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Flux<List<Data>> streamDelayWidow() {
		return Flux.range(1, 1000)
				.delayElements(Duration.ofMillis(10))
				.map(Long::valueOf)
				.map(aLong -> new Data(aLong, aLong + "-name"))
				.window(5).flatMap(Flux::collectList);
	}

	@GetMapping(value = "/companies", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Flux<CompanyInfo> companies() {
		return demoService.getCompanies();
	}

	@GetMapping(value = "/list-companies", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public List<CompanyInfo> listCompanies() {
		return demoService.getCompaniesList();
	}
}
