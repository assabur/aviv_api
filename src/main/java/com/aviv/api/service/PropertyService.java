package com.aviv.api.service;
import com.aviv.api.entity.Property;
import java.math.BigDecimal;
import java.util.List;

public interface PropertyService {
    List<Property> search(
            BigDecimal minPrice,
            BigDecimal maxPrice,
            BigDecimal minArea,
            BigDecimal maxArea,
            Integer roomCount,
            String city
    );
}
