package com.aviv.api.service;
import com.aviv.api.dto.PropertyDto;
import com.aviv.api.entity.Property;
import com.aviv.api.repository.PropertyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository repo;

    public PropertyServiceImpl(PropertyRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Property> search(
            BigDecimal minPrice,
            BigDecimal maxPrice,
            BigDecimal minArea,
            BigDecimal maxArea,
            Integer roomCount,
            String city,
            String zipcode
    ) {
        // On transforme city en motif LIKE insensible à la casse : %city%
        String cityPattern = (city == null || city.isBlank())
                ? null
                : "%" + city.trim().toLowerCase() + "%";

        return repo.search(minPrice, maxPrice, minArea, maxArea, roomCount, cityPattern,zipcode);
    }
}