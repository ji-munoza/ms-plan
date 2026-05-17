package cl.plataforma_gimnasio.ms_plan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MsPlanApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsPlanApplication.class, args);
	}

}
