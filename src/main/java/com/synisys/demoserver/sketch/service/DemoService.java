package com.synisys.demoserver.sketch.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synisys.demoserver.sketch.model.*;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Artush on 11/3/2017.
 */
@Service
public class DemoService {

	@Autowired
	CompanyService companyService;

	@Autowired
	AddressService addressService;

	@Autowired
	UserService userService;

	public Flux<CompanyInfo> getCompanies0() {
		Flux<UserData> users = userService.getUsers();

		return companyService.getCompanies().flatMap(companyData -> {
			Mono<Address> address = addressService.getAddressById(companyData.getAddressId());

			Mono<List<UserInfo>> listMono = users
					.filter(userData -> Objects.equals(userData.getCompanyId(), companyData.getId()))
					.map(userData -> new UserInfo(userData.getName(), userData.getRole(), userData.getStartDate()))
					.collectList();

			return Mono.zip(address, listMono)
					.map(tuple2 -> new CompanyInfo(companyData.getName(), tuple2.getT2(), tuple2.getT1()));
		});
	}

	public Flux<CompanyInfo> getCompanies() {
		return companyService.getCompanies().flatMap(companyData -> {
			Mono<Address> address = addressService.getAddressById(companyData.getAddressId());
			Mono<List<UserInfo>> users = userService.getUsersByCompanyId(companyData.getId()).collectList();
			return Mono.zip(address, users)
					.map(tuple2 -> new CompanyInfo(companyData.getName(), tuple2.getT2(), tuple2.getT1()));
		});
	}

	public List<CompanyInfo> getCompaniesList() {
		List<CompanyData> companyDataList = companyService.getCompanies().collectList().block();
		List<UserData> userDataList = userService.getUsers().collectList().block();

		List<CompanyInfo> companyInfoList = new ArrayList<>();
		for (CompanyData companyData : companyDataList) {
			Address address = addressService.getAddressById(companyData.getAddressId()).block();

			List<UserInfo> users = userDataList.stream()
					.filter(userData -> userData.getCompanyId().equals(companyData.getId()))
					.map(userData -> {
						Address userAddress = addressService.getAddressById(userData.getAddressId()).block();
						return new UserInfo(userData.getName(), userData.getRole(), userData.getStartDate(), userAddress.toString());
					})
					.collect(Collectors.toList());

			companyInfoList.add(new CompanyInfo(companyData.getName(), users, address));
		}

		return companyInfoList;
	}

	public List<CompanyInfo> getCompaniesCF() {

		List<CompanyInfo> companyInfos = new ArrayList<>();

		List<CompletableFuture> completableFutures = new ArrayList<>();

		companyService.getCompanies().collectList().toFuture().join().forEach(companyData -> {

			CompanyInfo companyInfo = new CompanyInfo(companyData.getName(), null, null);

			CompletableFuture<Address> addressCompletableFuture = addressService
					.getAddressById(companyData.getAddressId())
					.toFuture()
					.whenComplete((address, throwable) -> {
						companyInfo.setAddress(address);
					});

			List<UserInfo> userInfoList = new ArrayList<>();
			userService.getUsers().collectList().toFuture().whenComplete((userData, throwable) -> {
				userData.forEach(ud -> {
					addressService.getAddressById(ud.getAddressId()).toFuture().whenComplete((address, throwable1) -> {
						userInfoList.add(new UserInfo(ud.getName(), ud.getRole(), ud.getStartDate(), address.toString()));
					});
				});
			});

			CompletableFuture<List<UserInfo>> listCompletableFuture = userService
					.getUsersByCompanyId(companyData.getId())
					.collectList()
					.toFuture()
					.whenComplete((userInfos, throwable) -> {
						companyInfo.setUsers(userInfos);
					});

			companyInfos.add(companyInfo);

			completableFutures.add(addressCompletableFuture);
			completableFutures.add(listCompletableFuture);
		});

		CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0])).join();

		return companyInfos;
	}
}
