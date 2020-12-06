package com.czu.gramaticsandlanguages.foodPoint.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;

@Value
public class ProviderMenu {

    @JsonProperty("MenuList")
    List<MenuList> menuLists;

    @JsonProperty("Provider")
    String provider;
}
