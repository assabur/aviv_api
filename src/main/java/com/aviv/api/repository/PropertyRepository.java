package com.aviv.api.repository;
import com.aviv.api.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {

    @Query("""
        SELECT p FROM Property p
        WHERE (:minPrice IS NULL OR p.price >= :minPrice)
          AND (:maxPrice IS NULL OR p.price <= :maxPrice)
          AND (:minArea  IS NULL OR p.area  >= :minArea)
          AND (:maxArea  IS NULL OR p.area  <= :maxArea)
          AND (:roomCount IS NULL OR p.roomCount = :roomCount)
          AND (:cityPattern IS NULL OR LOWER(p.city) LIKE :cityPattern)
        """)
    List<Property> search(
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("minArea")  BigDecimal minArea,
            @Param("maxArea")  BigDecimal maxArea,
            @Param("roomCount") Integer roomCount,
            @Param("cityPattern") String cityPattern
    );
}
