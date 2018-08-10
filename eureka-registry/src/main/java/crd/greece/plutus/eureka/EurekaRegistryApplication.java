package crd.greece.plutus.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author chenjiayou022
 */
@EnableEurekaServer
@SpringBootApplication
public class EurekaRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaRegistryApplication.class, args);
	}
}
