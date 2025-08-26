package com.aviv.api.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertyDto {

    private String startDate;

    private BigDecimal price;

    private BigDecimal area;

    private BigDecimal siteArea;

    private Integer floor;

    private Integer roomCount;

    private Integer balconyCount;

    private Integer terraceCount;

    private String hasGarden;

    private String city;

    private String zipcode;

    private String hasPassengerLift;

    private String isNewConstruction;

    private Integer buildYear;

    private BigDecimal terraceArea;

    private String hasCellar;

    private Integer idTransactionType;

    private Integer idItemType;

    private Integer idItemSubType;
}
