package com.czu.gramaticsandlanguages.webScrapping.controller;

import com.appventure.AppBackend.webScrapping.service.NewsFeedService;
import com.appventure.AppBackend.webScrapping.service.UpcomingEventsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal")
public class InternalWebscrappingController {

    private final UpcomingEventsService upcomingEventsService;
    private final NewsFeedService newsFeedService;

    @GetMapping("/news")
    private void fetchNews() {
        newsFeedService.updateNewsFeed();
    }

    @GetMapping("/upcomingEvents")
    private void fetchUpcomingEvents() {
        upcomingEventsService.saveUpcomingEvents();
    }
}
