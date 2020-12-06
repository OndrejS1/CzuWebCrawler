package com.czu.gramaticsandlanguages.webScrapping.service;


import com.appventure.AppBackend.webScrapping.model.NewsArticle;

import java.util.List;

public interface ScrappingService {

    List<NewsArticle> scrape();

}
