package com.synisys.demoserver.demo;

import java.time.Duration;
import java.util.logging.Level;

import org.reactivestreams.Subscription;

import reactor.core.CoreSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * @author Artush on 11/6/2017.
 */
public class DemoBackpressure {

	public static void main(String[] args)
			throws InterruptedException {

//		invokeFastConsumer();

		invokeSlowConsumer();

		Thread.sleep(30000L);
	}

	private static void invokeSlowConsumer() {
		getData().delayElements(Duration.ofSeconds(3)).log().subscribe(new CoreSubscriber<Integer>() {
			public Subscription subscription;

			@Override
			public void onSubscribe(Subscription s) {
				this.subscription = s;
				this.subscription.request(1);
			}

			@Override
			public void onNext(Integer integer) {
				this.subscription.request(1);
			}

			@Override
			public void onError(Throwable t) {

			}

			@Override
			public void onComplete() {

			}
		});
	}

	private static void invokeFastConsumer() {
		getData().log().subscribe(new CoreSubscriber<Integer>() {
			public Subscription subscription;

			@Override
			public void onSubscribe(Subscription s) {
				this.subscription = s;
				this.subscription.request(1);
			}

			@Override
			public void onNext(Integer integer) {
				this.subscription.request(1);
			}

			@Override
			public void onError(Throwable t) {

			}

			@Override
			public void onComplete() {

			}
		});
	}

	private static Flux<Integer> getData() {
		return Flux.range(1, 100).map(integer -> {
			System.out.println("do expensive operation...");
			return integer;
		}).publishOn(Schedulers.newElastic("pub"), 2);
	}

}
