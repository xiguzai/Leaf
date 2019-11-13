package com.sankuai.inf.leaf.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.sankuai.inf.leaf.server", "com.sankuai.inf.leaf.admin"})
public class LeafServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeafServerApplication.class, args);
	}
}
