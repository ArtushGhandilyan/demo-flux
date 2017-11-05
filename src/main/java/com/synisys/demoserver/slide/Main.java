package com.synisys.demoserver.slide;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.reactivestreams.Subscription;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.CoreSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Artush on 11/4/2017.
 */
public class Main {

	private UserDao userDao;

	private ReactiveUserDao reactiveUserDao;

	public static void main(String[] args) {

		/*WebClient.create("https://exmple.com/api")
				.get()
				.uri("/users")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.flatMapMany(response -> response.bodyToFlux(User.class))
				.map(User::getName)
				.subscribe(System.out::println);*/

		/*Flux<Integer> j = Flux.just(1, 2, 3, 4, 5);

		j.map(i -> i * 10).subscribe(System.out::println);

		j.map(i -> i + 5).subscribe(System.out::println);

		Stream<Integer> j = Arrays.asList(1, 2, 3, 4, 5).stream();

		j.map(i -> i * 10).forEach(System.out::println);

		j.map(i -> i + 5).forEach(System.out::println);*/

	}

	public void slide(String[] args) {

		/*List<User> users = userDao.getUsers();
		List<String> names = new ArrayList<String>();
		for (int i = 0; i < users.size(); ++i) {
			names.add(users.get(i).getName());
		}*/

		reactiveUserDao.getUserById(144).block();
		reactiveUserDao.getUsers().blockFirst();
		reactiveUserDao.getUsers().blockLast();

		reactiveUserDao.getUsers().subscribe(user -> {
			System.out.println(user.getName());
		});

		reactiveUserDao.getUsers().map(user -> user.getName()).subscribe(new CoreSubscriber<String>() {
			@Override
			public void onSubscribe(Subscription s) {

			}

			@Override
			public void onNext(String s) {

			}

			@Override
			public void onError(Throwable t) {

			}

			@Override
			public void onComplete() {

			}
		});

		List<String> names = userDao.getUsers().stream().map(user -> user.getName()).collect(Collectors.toList());

		System.out.println(names);

	}
}

class UserDao {
	public List<User> getUsers() {
		return null;
	}
}

class ReactiveUserDao {
	public Flux<User> getUsers() {
		return Flux.range(1, 100).map(String::valueOf).map(User::new).delayElements(Duration.ofMillis(10));
	}

	public Mono<User> getUserById(Integer id) {
		return null;
	}
}

class User {
	String name;

	public User() {
	}

	public User(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
