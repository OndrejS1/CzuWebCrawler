package com.czu.gramaticsandlanguages.foodPoint.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Data
public class MenuList {

    @JsonProperty("DateFrom")
    Date dateFrom;

    @JsonProperty("DateTo")
    Date dateTo;

    @JsonProperty("FoodType")
    String foodType;

    @JsonProperty("FullPrice")
    String fullPrice;

    @JsonProperty("StudentPrice")
    String studentPrice;

    @JsonProperty("Name")
    String name;

}
