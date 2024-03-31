package com.virginonline.mediasoft.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Getter
@Setter
@Builder
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  @Column(name = "name", nullable = false)
  private String name;
  @Column(unique = true, name = "article", nullable = false)
  private Long article;
  @Column(name = "description")
  private String description;
  @Column(name = "category")
  private String category;
  @Column(name = "price", nullable = false)
  private BigDecimal price;
  @Column(name = "quantity", nullable = false)
  private Long quantity;
  @CreationTimestamp
  @Column(name = "created_at", updatable = false, nullable = false)
  private Instant createdAt;
  @UpdateTimestamp
  @Column(name = "updated_at")
  private Instant updatedAt;
}
