package com.czu.gramaticsandlanguages.homepage;

import com.appventure.AppBackend.foodPoint.service.FoodpointService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomepageController {

    private final FoodpointService foodpointService;

    public HomepageController(FoodpointService foodpointService) {
        this.foodpointService = foodpointService;
    }

    @RequestMapping("/")
    public String homepage(Model model) {
        model.addAttribute("foods", foodpointService.getFoodByDateAndFoodValidityRange(1, 4));
        return "homepage";
    }
}
