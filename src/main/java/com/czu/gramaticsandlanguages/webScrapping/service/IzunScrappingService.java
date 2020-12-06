package com.czu.gramaticsandlanguages.webScrapping.service;

import com.appventure.AppBackend.webScrapping.model.NewsArticle;
import com.appventure.AppBackend.webScrapping.model.WebsiteSource;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class IzunScrappingService implements ScrappingService {

    private static final String BASE_URL = "https://izun.eu";
    private static final String FULL_URL = "https://izun.eu/clanky/Aktuality";

    @Override
    public List<NewsArticle> scrape() {
        Document page;
        try {
            page = Jsoup.connect(FULL_URL).get();
            Elements news = page.select(".article-item");
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd. MM. yyyy");

            return news
                    .stream()
                    .map(e -> new NewsArticle(
                            null,
                            e.getAllElements().select("div:nth-child(2) > h4").text(),
                            LocalDate.parse(e.getAllElements().select("div:nth-child(2) > span:nth-child(1)").text(), dateTimeFormatter),
                            null,
                            BASE_URL + e.getAllElements().attr("src"),
                            BASE_URL + e.getElementsByClass("read").attr("href"),
                            WebsiteSource.IZUN))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Failed to scrape data from IzunScrappingService");
        }
    }
}
