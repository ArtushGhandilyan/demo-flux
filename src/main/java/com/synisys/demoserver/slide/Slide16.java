package com.synisys.demoserver.slide;

import java.time.Duration;

import reactor.core.publisher.Flux;

/**
 * @author Artush on 11/4/2017.
 */
public class Slide16 {

	public static void main(String[] args)
			throws InterruptedException {

		Flux<String> elements = Flux.range(1, 100).map(String::valueOf).delayElements(Duration.ofMillis(10));

		elements.subscribe(integer -> {
			System.out.println(integer);
		});

	}
}
