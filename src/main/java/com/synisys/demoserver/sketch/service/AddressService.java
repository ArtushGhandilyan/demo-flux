package com.synisys.demoserver.sketch.service;

import org.springframework.stereotype.Service;

import com.synisys.demoserver.sketch.model.Address;

import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * @author Artush on 11/3/2017.
 */
@Service
public class AddressService {
	public Mono<Address> getAddressById(Long addressId) {
		return Mono.just(new Address("city-" + addressId, "street-" + addressId, "building-" + addressId)).delayElement(Duration.ofMillis(100));
	}
}
