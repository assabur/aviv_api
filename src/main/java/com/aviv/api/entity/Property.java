package com.aviv.api.entity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;


@Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "property", schema = "public")
public class Property {
    @Id
    @Column(name = "id_property")
    private Long id;


    @Column(name = "start_date")
    private String startDate;


    @Column(name = "price")
    private BigDecimal price;


    @Column(name = "area")
    private BigDecimal area;


    @Column(name = "site_area")
    private BigDecimal siteArea;


    @Column(name = "floor")
    private Integer floor;


    @Column(name = "room_count")
    private Integer roomCount;


    @Column(name = "balcony_count")
    private Integer balconyCount;


    @Column(name = "terrace_count")
    private Integer terraceCount;


    @Column(name = "has_garden")
    private String hasGarden;


    @Column(name = "city")
    private String city;


    @Column(name = "zipcode")
    private String zipcode;


    @Column(name = "has_passenger_lift")
    private String hasPassengerLift;


    @Column(name = "is_new_construction")
    private String isNewConstruction;


    @Column(name = "build_year")
    private Integer buildYear;


    @Column(name = "terrace_area")
    private BigDecimal terraceArea;


    @Column(name = "has_cellar")
    private String hasCellar;

    @Column(name = "id_transaction_type")
    private Integer idTransactionType;

    @Column(name = "id_item_type")
    private Integer idItemType;

    @Column(name = "id_item_sub_type")
    private Integer idItemSubType;

}


