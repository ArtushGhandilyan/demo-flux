package com.synisys.demoserver.present;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.synisys.demoserver.sketch.model.CompanyInfo;
import com.synisys.demoserver.sketch.service.DemoService;

import reactor.core.publisher.Flux;

/**
 * @author Artush on 11/3/2017.
 */
@RestController
@RequestMapping("/demo")
public class ParallelComputingController {

	@Autowired
	DemoService demoService;

	@GetMapping(value = "/stream-companies2", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Flux<CompanyInfo> streamCompanies0() {
		return demoService.getCompanies0();
	}

	@GetMapping(value = "/stream-companies", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Flux<CompanyInfo> streamCompanies() {
		return demoService.getCompanies();
	}

	@GetMapping(value = "/list-companies", produces = MediaType.APPLICATION_JSON_VALUE)
	public Flux<CompanyInfo> listCompanies() {
		return demoService.getCompanies();
	}

	@GetMapping(value = "/list-companies2", produces = MediaType.APPLICATION_JSON_VALUE)
	public Flux<CompanyInfo> listCompanies2() {
		return demoService.getCompanies0();
	}

	@GetMapping(value = "/companies", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<CompanyInfo> companies() {
		return demoService.getCompaniesList();
	}

	@GetMapping(value = "/completable-future-companies", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<CompanyInfo> cfCompanies() {
		return demoService.getCompaniesCF();
	}
}
