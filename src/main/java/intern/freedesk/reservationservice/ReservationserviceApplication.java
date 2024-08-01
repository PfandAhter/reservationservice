package intern.freedesk.reservationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ReservationserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservationserviceApplication.class, args);
	}

}
