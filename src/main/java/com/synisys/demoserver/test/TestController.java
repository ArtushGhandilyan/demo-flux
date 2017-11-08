package com.synisys.demoserver.test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.synisys.demoserver.sketch.service.DemoService;

import reactor.core.publisher.Flux;

/**
 * @author Artush on 11/3/2017.
 */
@RestController
@RequestMapping("/test")
public class TestController {

	@Autowired
	DemoService demoService;

	@GetMapping(value = "/stream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Flux<TestModel> stream() {
		return Flux.range(1, 1000)/*.delayElements(Duration.ofMillis(10))*/
				.map(TestModel::new);
	}

	@GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public Flux<TestModel> list() {
		return Flux.range(1, 1000)/*.delayElements(Duration.ofMillis(300))*/
				.map(TestModel::new);
	}

	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<TestModel> handle()
			throws InterruptedException {
		List<TestModel> result = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			Thread.sleep(300);
			result.add(new TestModel(i));
		}
		return result;
	}
}



//docker run --volume=/:/rootfs:ro --volume=/var/run:/var/run:rw --volume=/sys:/sys:ro --volume=/var/lib/docker/:/var/lib/docker:ro --volume=/dev/disk/:/dev/disk:ro --publish=8080:8080 --detach=true --name=cadvisor google/cadvisor:latest
//docker run --publish=8070:8080 --detach=true --name=cadvisor google/cadvisor:latest