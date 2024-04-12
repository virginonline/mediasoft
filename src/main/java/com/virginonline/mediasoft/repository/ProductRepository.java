package com.virginonline.mediasoft.repository;

import com.virginonline.mediasoft.domain.Product;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, UUID> {

  @Query(value = "select p from Product p where p.category like ?1")
  List<Product> findAllByCategory(String category, Pageable pageable);

  @Query("select (count(p) > 0) from Product p where p.article = ?1")
  Boolean existsByArticle(Long article);

  @Query(value = "select p from Product p")
  List<Product> findAllPaginated(Pageable pageable);
}
