package com.czu.gramaticsandlanguages.webScrapping.service;

import com.appventure.AppBackend.webScrapping.model.NewsArticle;
import com.appventure.AppBackend.webScrapping.model.WebsiteSource;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PefScrappingService implements ScrappingService {

    private static final String BASE_URL = "https://www.pef.czu.cz/";
    private static final String FULL_URL = "https://www.pef.czu.cz/cs/r-7006-o-fakulte/r-7179-aktuality";

    @Override
    public List<NewsArticle> scrape() {
        Document page;

        try {
            page = Jsoup.connect(FULL_URL).get();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d.M.y");
            Elements news = page.select(".article-card");

            return news
                    .stream()
                    .map(e -> new NewsArticle(
                            null,
                            e.getElementsByClass("card-title").text(),
                            LocalDate.parse(e.getElementsByClass("date").text(), dateTimeFormatter),
                            e.getElementsByClass("card-text").text(),
                            BASE_URL + e.getElementsByClass("card-img-top").attr("src"),
                            BASE_URL + e.getElementsByClass("btn btn-default featured-btn").attr("href"),
                            WebsiteSource.PEF))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Failed to scrape data from PefScrappingService");
        }
    }
}
