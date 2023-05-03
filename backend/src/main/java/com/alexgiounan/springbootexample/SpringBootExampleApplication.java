package com.alexgiounan.springbootexample;

import com.alexgiounan.springbootexample.customer.Customer;
import com.alexgiounan.springbootexample.customer.CustomerRepository;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@SpringBootApplication
public class SpringBootExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootExampleApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(CustomerRepository customerRepository){
		return  args -> {
			var faker = new Faker();
			Random random = new Random();
			Name name = faker.name();
			String firstName = name.firstName();
			String lastName = name.lastName();

			Customer customer = Customer.builder()
					.name(firstName + " " + lastName)
					.email(firstName.toLowerCase() + "." + lastName.toLowerCase() + "@gmail.com")
					.age(random.nextInt(16,99))
					.build();

			customerRepository.save(customer);

		};
	}
}
