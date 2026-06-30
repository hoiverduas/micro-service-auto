package flota_servicer.flota_servicer;

import jdk.jfr.Enabled;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class FlotaServicerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlotaServicerApplication.class, args);
	}

}
