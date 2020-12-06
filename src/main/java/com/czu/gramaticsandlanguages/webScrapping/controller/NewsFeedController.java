package com.czu.gramaticsandlanguages.webScrapping.controller;

import com.appventure.AppBackend.webScrapping.model.NewsArticle;
import com.appventure.AppBackend.webScrapping.model.WebsiteSource;
import com.appventure.AppBackend.webScrapping.service.NewsFeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsFeedController {

    private final NewsFeedService newsFeedService;

    @GetMapping("/feed")
    public List<NewsArticle> getFeed(
            @RequestParam WebsiteSource websiteSource) {
        return newsFeedService.getFeed(websiteSource);
    }

    @GetMapping("/updateFeed")
    public void updateFeed() {
         newsFeedService.updateNewsFeed();
    }

    @GetMapping("/widgetFeed")
    public List<NewsArticle> getLastFiveFeed(
            @RequestParam WebsiteSource websiteSource) {
        return newsFeedService.getLastFiveFeed(websiteSource);
    }
}
