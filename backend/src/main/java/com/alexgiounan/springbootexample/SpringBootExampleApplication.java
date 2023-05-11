package com.alexgiounan.springbootexample;

import com.alexgiounan.springbootexample.customer.Customer;
import com.alexgiounan.springbootexample.repository.CustomerRepository;
import com.alexgiounan.springbootexample.customer.Gender;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
			int age = random.nextInt(16, 99);
			Gender gender = age % 2 == 0 ? Gender.MALE : Gender.FEMALE;

			Customer customer = Customer.builder()
					.name(firstName + " " + lastName)
					.email(firstName.toLowerCase() + "." + lastName.toLowerCase() + "@gmail.com")
					.age(age)
					.gender(gender)
					.build();

			customerRepository.save(customer);

		};
	}
}
