package org.bana.employeemanager;

import java.util.Arrays;

import com.github.javafaker.Faker;

import org.bana.employeemanager.model.Employee;
import org.bana.employeemanager.model.UserApp;
import org.bana.employeemanager.repo.UserRepository;
import org.bana.employeemanager.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
public class EmployeemanagerApplication implements CommandLineRunner {
	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(EmployeemanagerApplication.class, args);
	}

	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		// *
		corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
		corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
				"Accept", "Authorization", "Origin, Accept", "X-Requested-With", "Access-Control-Request-Method",
				"Access-Control-Request-Headers"));
		corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization",
				"Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}

	@Override
	public void run(String... args) throws Exception {
		Faker faker = new Faker();
		Employee employee;
		for (int i = 0; i < 50; i++) {
			employee = new Employee();
			employee.setName(faker.name().fullName());
			employee.setJobTitle(faker.job().title());
			employee.setPhone(faker.phoneNumber().phoneNumber());
			employeeService.addEmployee(employee);
		}

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		userRepository.save(new UserApp("user", encoder.encode("user"), "ROLE_USER"));

		userRepository.save(new UserApp("admin", encoder.encode("admin"), "ROLE_ADMIN,ROLE_USER"));

	}

}
