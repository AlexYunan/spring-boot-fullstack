package com.alexgiounan.springbootexample.customer;

import com.alexgiounan.springbootexample.AbstractTestcontainers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryTest extends AbstractTestcontainers {

    @Autowired
    private CustomerRepository underTest;

    @Autowired
    private ApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        underTest.deleteAll();
        System.out.println(applicationContext.getBeanDefinitionCount());
    }

    @Test
    void existsCustomerByEmail() {
        // Given
        String fakerEmail = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();

        Customer customer = Customer.builder()
                .name(FAKER.name().fullName())
                .email(fakerEmail)
                .age(20)
                .build();

        underTest.save(customer);

        // When

        var actual = underTest.existsCustomerByEmail(fakerEmail);

        // Then

        assertThat(actual).isTrue();
    }

    @Test
    void existsCustomerByEmailFailsWhenEmailNotPresent() {
        // Given
        String fakerEmail = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();

        // When

        var actual = underTest.existsCustomerByEmail(fakerEmail);

        // Then

        assertThat(actual).isFalse();
    }

    @Test
    void existsCustomerById() {
        // Given
        String fakerEmail = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();

        Customer customer = Customer.builder()
                .name(FAKER.name().fullName())
                .email(fakerEmail)
                .age(20)
                .build();

        underTest.save(customer);

        Integer id = underTest.findAll()
                .stream()
                .filter(c -> c.getEmail().equals(fakerEmail))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();
        // When

        var actual = underTest.existsCustomerById(id);

        // Then

        assertThat(actual).isTrue();

    }

    @Test
    void existsCustomerByIdWhenIdNotPresent() {
        // Given
        var id = -1;
        // When

        var actual = underTest.existsCustomerById(id);

        // Then

        assertThat(actual).isFalse();

    }
}