package com.czu.gramaticsandlanguages.foodPoint.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Value
public class FoodPointResource {

    @JsonProperty("MenuDate")
    String menuDate;

    @JsonProperty("ProviderMenu")
    List<ProviderMenu> providerMenus;


    public FoodPointResource(@JsonProperty("MenuDate") Date menuDate, @JsonProperty("ProviderMenu") List<ProviderMenu> providerMenus) {
        this.menuDate = convertDate(menuDate);
        this.providerMenus = providerMenus;
    }

    private String convertDate(Date menuDate) {
        return new SimpleDateFormat("d. M. yyyy")
                .format(menuDate);
    }
}

