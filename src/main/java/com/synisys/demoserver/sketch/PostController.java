package com.synisys.demoserver.sketch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Artush on 11/4/2017.
 */
public class PostController {

	private PostRepository repository;

	public PostController(PostRepository repository) {
		this.repository = repository;
	}

	@GetMapping("/posts")
	Flux<Post> list() {
		return this.repository.findAll();
	}

	@GetMapping("/posts/{id}")
	Mono<Post> findById(@PathVariable String id) {
		return this.repository.findOne(id);
	}

	@GetMapping("/posts/titles")
	Flux<String> getPostTitles() {
		return this.repository.findAll()
				.filter(Post::hasComment)
				.map(Post::getTitle);
	}
}


class Post {

	String getTitle() {
		return null;
	}

	boolean hasComment() {
		return false;
	}
}

class PostRepository {
	Flux<Post> findAll() {
		return null;
	}

	Mono<Post> findOne(String id) {
		return null;
	}
}
