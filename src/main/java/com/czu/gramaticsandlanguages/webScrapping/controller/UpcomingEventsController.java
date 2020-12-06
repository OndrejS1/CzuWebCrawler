package com.czu.gramaticsandlanguages.webScrapping.controller;

import com.appventure.AppBackend.webScrapping.model.UpcomingEvent;
import com.appventure.AppBackend.webScrapping.model.WebsiteSource;
import com.appventure.AppBackend.webScrapping.service.UpcomingEventsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/upcomingEvents")
@RequiredArgsConstructor
public class UpcomingEventsController {

    private final UpcomingEventsService upcomingEventsService;

    @GetMapping("/getFeed")
    public List<UpcomingEvent> getUpcomingEvents(
            @RequestParam WebsiteSource websiteSource) {
        return upcomingEventsService.getUpcomingEvents(websiteSource);
    }
}
