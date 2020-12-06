package com.czu.gramaticsandlanguages.foodPoint.controller;


import com.appventure.AppBackend.foodPoint.resource.FoodPointResource;
import com.appventure.AppBackend.foodPoint.service.FoodpointService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/foodpoint")
public class FoodPointController {

    private final FoodpointService foodpointService;

    @GetMapping("/getProviders")
    public String[] getProviders() {
        return foodpointService.getFoodProviders();
    }

    @GetMapping("/getMenuByDate")
    public List<FoodPointResource> getMenuByDate(
            @RequestParam @NotBlank int days,
            @RequestParam @NotBlank int range) {
        return foodpointService.getFoodByDateAndFoodValidityRange(days, range);
    }
}
