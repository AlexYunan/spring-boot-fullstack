package com.alexgiounan.springbootexample.customer;

import com.alexgiounan.springbootexample.AbstractTestcontainers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class CustomerJDBCDataAccessServiceTest extends AbstractTestcontainers {

    private CustomerJDBCDataAccessService underTest;

    private final CustomerRowMapper customerRowMapper = new CustomerRowMapper();


    @BeforeEach
    void setUp() {
        underTest = new CustomerJDBCDataAccessService(
                getJdbcTemplate(),
                customerRowMapper
        );
    }

    @Test
    void selectAllCustomers() {
        // Given
        Customer customer = Customer.builder()
                .name(FAKER.name().fullName())
                .email(FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID())
                .age(20)
                .gender(Gender.MALE)
                .build();

        underTest.insertCustomer(customer);
        // When

        List<Customer> actual = underTest.selectAllCustomers();


        // Then
        assertThat(actual).isNotEmpty();
    }

    @Test
    void selectCustomerById() {
        // Given
        String fakerEmail = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();

        Customer customer = Customer.builder()
                .name(FAKER.name().fullName())
                .email(fakerEmail)
                .age(20)
                .gender(Gender.MALE)
                .build();
        underTest.insertCustomer(customer);

        Integer id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(fakerEmail))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();
        // When

        Optional<Customer> actual = underTest.selectCustomerById(id);

        // Then

        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
            assertThat(c.getAge()).isEqualTo(customer.getAge());
        });
    }

    @Test
    void willReturnEmptyWhenSelectCustomerById() {
        // Given
        int id = -1;

        // When
        var actual = underTest.selectCustomerById(id);

        // Then
        assertThat(actual).isEmpty();
    }


    @Test
    void existsCustomerWithEmail() {
        // Given
        String fakerEmail = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();

        Customer customer = Customer.builder()
                .name(FAKER.name().fullName())
                .email(fakerEmail)
                .age(20)
                .gender(Gender.MALE)
                .build();

        underTest.insertCustomer(customer);
        // When

        boolean actual = underTest.existsCustomerWithEmail(fakerEmail);
        // Then
        assertThat(actual).isTrue();
    }

    @Test
    void existsPersonWithEmailReturnsFalseWhenDoesNotExists() {
        // Given
        String fakerEmail = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        // When
        boolean actual = underTest.existsCustomerWithEmail(fakerEmail);
        // Then
        assertThat(actual).isFalse();
    }

    @Test
    void deleteCustomerById() {
        // Given
        String fakerEmail = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();

        Customer customer = Customer.builder()
                .name(FAKER.name().fullName())
                .email(fakerEmail)
                .age(20)
                .gender(Gender.MALE)
                .build();

        underTest.insertCustomer(customer);
        Integer id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(fakerEmail))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();
        // When

        underTest.deleteCustomerById(id);
        // Then
        Optional<Customer> actual = underTest.selectCustomerById(id);
        assertThat(actual).isNotPresent();
    }

    @Test
    void existsPersonWithId() {
        // Given
        String fakerEmail = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();

        Customer customer = Customer.builder()
                .name(FAKER.name().fullName())
                .email(fakerEmail)
                .age(20)
                .gender(Gender.MALE)
                .build();

        underTest.insertCustomer(customer);

        Integer id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(fakerEmail))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();
        // When
        boolean actual = underTest.existsPersonWithId(id);

        // Then
        assertThat(actual).isTrue();
    }

    @Test
    void updateCustomerName() {
        // Given
        String fakerEmail = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();

        Customer customer = Customer.builder()
                .name(FAKER.name().fullName())
                .email(fakerEmail)
                .age(20)
                .gender(Gender.MALE)
                .build();

        underTest.insertCustomer(customer);

        Integer id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(fakerEmail))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        var newName = "foo";


        // When
        Customer update = new Customer();
        update.setId(id);
        update.setName(newName);

        underTest.updateCustomer(update);

        // Then
        Optional<Customer> actual = underTest.selectCustomerById(id);

        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(newName);
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
            assertThat(c.getAge()).isEqualTo(customer.getAge());
        });
    }

    @Test
    void updateCustomerAge() {
        // Given
        String fakerEmail = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();

        Customer customer = Customer.builder()
                .name(FAKER.name().fullName())
                .email(fakerEmail)
                .age(20)
                .gender(Gender.MALE)
                .build();

        underTest.insertCustomer(customer);

        Integer id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(fakerEmail))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        var newAge = 100;


        // When
        Customer update = new Customer();
        update.setId(id);
        update.setAge(newAge);

        underTest.updateCustomer(update);

        // Then
        Optional<Customer> actual = underTest.selectCustomerById(id);

        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
            assertThat(c.getAge()).isEqualTo(newAge);
        });
    }

    @Test
    void updateCustomerEmail() {
        // Given
        String fakerEmail = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();

        Customer customer = Customer.builder()
                .name(FAKER.name().fullName())
                .email(fakerEmail)
                .age(20)
                .gender(Gender.MALE)
                .build();

        underTest.insertCustomer(customer);

        Integer id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(fakerEmail))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        var newEmail =  FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();



        // When
        Customer update = new Customer();
        update.setId(id);
        update.setEmail(newEmail);

        underTest.updateCustomer(update);

        // Then
        Optional<Customer> actual = underTest.selectCustomerById(id);

        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getEmail()).isEqualTo(newEmail);
            assertThat(c.getAge()).isEqualTo(customer.getAge());
        });
    }
    @Test
    void willUpdateAllPropertiesCustomer() {
        // Given
        String fakerEmail = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = Customer.builder()
                .name(FAKER.name().fullName())
                .email(fakerEmail)
                .age(20)
                .gender(Gender.MALE)
                .build();

        underTest.insertCustomer(customer);

        int id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(fakerEmail))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        // When update with new name, age and email
        Customer update = new Customer();
        update.setId(id);
        update.setName("foo");
        String newEmail = UUID.randomUUID().toString();
        update.setEmail(newEmail);
        update.setAge(22);

        underTest.updateCustomer(update);

        // Then
        Optional<Customer> actual = underTest.selectCustomerById(id);

        assertThat(actual).isPresent().hasValueSatisfying(updated -> {
            assertThat(updated.getId()).isEqualTo(id);
            assertThat(updated.getGender()).isEqualTo(Gender.MALE);
            assertThat(updated.getName()).isEqualTo("foo");
            assertThat(updated.getEmail()).isEqualTo(newEmail);
            assertThat(updated.getAge()).isEqualTo(22);
        });
    }
}