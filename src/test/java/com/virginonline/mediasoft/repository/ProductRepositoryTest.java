package com.virginonline.mediasoft.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.virginonline.mediasoft.criteria.JpaSpecificationsBuilder;
import com.virginonline.mediasoft.criteria.Operation;
import com.virginonline.mediasoft.criteria.filter.NameFilter;
import com.virginonline.mediasoft.criteria.filter.NumericFilter;
import com.virginonline.mediasoft.domain.Product;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
public class ProductRepositoryTest {

  private static final Logger log = LoggerFactory.getLogger(ProductRepositoryTest.class);

  @Container
  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

  PageRequest page = PageRequest.of(0, 20);
  @Autowired ProductRepository repository;
  JpaSpecificationsBuilder<Product> builder = new JpaSpecificationsBuilder<>();

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.liquibase.enabled", () -> "false");
    registry.add("spring.jpa.show-sql", () -> "false");
    registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    registry.add("spring.datasource.url", postgres::getJdbcUrl);
    registry.add("spring.datasource.username", postgres::getUsername);
    registry.add("spring.datasource.password", postgres::getPassword);
  }

  @BeforeEach
  void setUp() {
    repository.deleteAll();
    var products =
        IntStream.range(0, 100)
            .mapToObj(
                i ->
                    Product.builder()
                        .id(UUID.randomUUID())
                        .name("Product " + i)
                        .article(Long.parseLong(String.valueOf(i + 11)))
                        .description("Description " + i)
                        .category("Category " + i)
                        .quantity(1L)
                        .price(new BigDecimal((i + 1) * 2))
                        .build())
            .toList();
    repository.saveAll(products);
  }

  @Test
  void shouldFindAll() {
    var products = repository.findAll();
    assertThat(products.size()).isEqualTo(100);
  }

  @Test
  void shouldFindAllBySingleCriteria() {
    var nameFilter = new NameFilter(Operation.EQUALS, "Product 6");
    log.info(
        "criteria: {} {} {}",
        nameFilter.getField(),
        nameFilter.getOperation(),
        nameFilter.getValue());
    var specs = builder.buildSpecification(List.of(nameFilter));
    var products = repository.findAll(specs, page).toList();
    assertThat(products.size()).isEqualTo(1);
  }

  @Test
  void shouldFindAllByComplexCriteria() {
    var specs =
        builder.buildSpecification(
            List.of(
                new NameFilter(Operation.LIKE, "Product 6"),
                new NumericFilter(Operation.LESS_THAN, new BigDecimal(130)),
                new NumericFilter(Operation.GREATER_THAN, new BigDecimal(20))));
    var products = repository.findAll(specs, page).getContent();
    assertThat(products.size()).isEqualTo(4);
  }
}
