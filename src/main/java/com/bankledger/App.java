package com.bankledger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @AUTHOR: sandnul025
 * @MOTTO: Rainbow comes after a storm.
 * @DATE: 2017年12月4日 下午12:24:33
 */

@SpringBootApplication
@EnableScheduling
@ServletComponentScan
public class App /*extends SpringBootServletInitializer*/ {

	/*@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {

		return application.sources(App.class);
	}*/

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
