package com.virginonline.mediasoft.config;

import com.virginonline.mediasoft.repository.ProductRepository;
import com.virginonline.mediasoft.scheduling.AbstractPriceChangeScheduled;
import com.virginonline.mediasoft.scheduling.price.OptimizedPriceChangeScheduled;
import com.virginonline.mediasoft.scheduling.price.StandardPriceChangeScheduled;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@EnableAspectJAutoProxy
@RequiredArgsConstructor
public class ApplicationConfig {

  private final ProductRepository productRepository;
  private final JdbcTemplate jdbcTemplate;

  @Value("${app.scheduling.optimized}")
  private boolean optimized;

  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
        .info(new Info().title("Products api").description("product api docs").version("1.0.0"));
  }

  @Profile("prod")
  @Bean
  @ConditionalOnProperty(name = "app.scheduling.enabled", havingValue = "true")
  public AbstractPriceChangeScheduled priceChangeScheduled() {
    return optimized
        ? new OptimizedPriceChangeScheduled(jdbcTemplate)
        : new StandardPriceChangeScheduled(productRepository);
  }
}
