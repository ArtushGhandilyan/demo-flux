package com.synisys.demoserver.sketch.service;

import java.time.Duration;
import java.util.Date;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.synisys.demoserver.sketch.model.UserData;
import com.synisys.demoserver.sketch.model.UserInfo;

import reactor.core.publisher.Flux;

/**
 * @author Artush on 11/3/2017.
 */
@Service
public class UserService {

	public Flux<UserInfo> getUsersByCompanyId(Long companyId) {
		return getUsers().filter(userData -> Objects.equals(userData.getCompanyId(), companyId))
				.map(userData -> new UserInfo(userData.getName(), userData.getRole(), userData.getStartDate()));
	}

	public Flux<UserData> getUsers() {
		return Flux.range(1, 100).delayElements(Duration.ofMillis(30)).map(this::getDummyUserData);
	}

	private UserData getDummyUserData(int index) {
		UserData data = new UserData();
		data.setId((long) index);
		data.setCompanyId((long) (index % 10));
		data.setAddressId(0L);
		data.setName("username-" + index);
		data.setRole("user-" + index);
		data.setStartDate(new Date());
		return data;
	}
}
