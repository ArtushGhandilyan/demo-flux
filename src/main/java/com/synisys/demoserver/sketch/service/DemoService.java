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
					.map(userData -> new UserInfo(userData.getName(), userData.getRole(), userData.getStartDate()))
					.collect(Collectors.toList());

			companyInfoList.add(new CompanyInfo(companyData.getName(), users, address));
		}

		return companyInfoList;
	}

	class Temp {
		public CompletableFuture<Address> addressCompletableFuture;
		public CompletableFuture<List<UserInfo>> listCompletableFuture;
		public CompanyData companyData;

		public Temp(CompletableFuture<Address> addressCompletableFuture,
				CompletableFuture<List<UserInfo>> listCompletableFuture, CompanyData companyData) {
			this.addressCompletableFuture = addressCompletableFuture;
			this.listCompletableFuture = listCompletableFuture;
			this.companyData = companyData;
		}
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
