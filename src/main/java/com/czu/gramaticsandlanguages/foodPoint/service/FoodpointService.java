package com.czu.gramaticsandlanguages.foodPoint.service;

import com.appventure.AppBackend.foodPoint.resource.FoodPointResource;
import com.appventure.AppBackend.foodPoint.resource.ProviderMenu;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class FoodpointService {

    private final String BASE_URL = "https://wcavp-media-ws.czu.cz/api/Food";
    private final String LIST_MENU_BY_DATE_PATH = "/ListMenuByDate";
    private final String LIST_PROVIDERS = "/ListProviders";

    private final RestTemplate restTemplate;

    public FoodpointService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public String[] getFoodProviders() {
        String url = BASE_URL + LIST_PROVIDERS;

        return restTemplate.getForObject(url, String[].class);
    }

    public List<FoodPointResource> getFoodByDateAndFoodValidityRange(int days, int range) {
        String url = BASE_URL + LIST_MENU_BY_DATE_PATH;

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("ListDays", days);

        FoodPointResource[] response = restTemplate.postForObject(url, map, FoodPointResource[].class);
        List<FoodPointResource> foodPointResources = Arrays.asList(Objects.requireNonNull(response));

        foodPointResources.stream()
                .map(FoodPointResource::getProviderMenus)
                .flatMap(Collection::stream)
                .map(ProviderMenu::getMenuLists)
                .forEach(food -> food.removeIf(s -> {
                    s.setName(filterAllergens(s.getName()));
                    return DAYS.between(s.getDateFrom().toInstant(), s.getDateTo().toInstant()) > range;
                }));

        foodPointResources.stream()
                .map(FoodPointResource::getProviderMenus)
                .forEach(t -> t.removeIf(s -> s.getMenuLists().isEmpty()));

        return foodPointResources;
    }

    private String filterAllergens(String foodName) {
        String filterResult1 = foodName.replaceAll("\\(([^)]+)\\)","");

        return filterResult1.replaceAll("A:.*","");
    }
}
