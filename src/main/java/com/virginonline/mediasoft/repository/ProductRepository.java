package com.virginonline.mediasoft.repository;

import com.virginonline.mediasoft.domain.Product;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository
    extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {

  @Query("select (count(p) > 0) from Product p where p.article = ?1")
  Boolean existsByArticle(Long article);
}
