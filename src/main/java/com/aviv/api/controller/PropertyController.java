package com.aviv.api.controller;
import com.aviv.api.dto.PropertyDto;
import com.aviv.api.mapper.PropertyMapper;
import com.aviv.api.response.ApiResponse;
import com.aviv.api.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;


@RestController
@RequestMapping("api/v1/properties")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService service;
    private final PropertyMapper mapper;

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<PropertyDto>>> search(
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) BigDecimal minArea,
            @RequestParam(required = false) BigDecimal maxArea,
            @RequestParam(required = false) Integer roomCount,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String zipcode
    ) {
        // petite validation
        if (minPrice != null && maxPrice != null && minPrice.compareTo(maxPrice) > 0) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.of(400, "minPrice ne doit pas être > maxPrice", null)
            );
        }
        if (minArea != null && maxArea != null && minArea.compareTo(maxArea) > 0) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.of(400, "minArea ne doit pas être > maxArea", null)
            );
        }

        List<PropertyDto> data = service.search(minPrice, maxPrice, minArea, maxArea, roomCount, city,zipcode)
                .stream()
                .map(mapper::toDto)
                .toList();



        return ResponseEntity.ok(
                ApiResponse.of(200, "Propriétés trouvées : " + data.size(), data)
        );
    }
}
