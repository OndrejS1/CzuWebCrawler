package com.czu.gramaticsandlanguages.webScrapping.service;

import com.appventure.AppBackend.aleph.exception.AlephException;
import com.appventure.AppBackend.webScrapping.model.NewsArticle;
import com.appventure.AppBackend.webScrapping.model.WebsiteSource;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AlephScrappingService implements ScrappingService {

    private static final String BASE_URL = "https://lib.czu.cz/";
    private static final String FULL_URL = "https://lib.czu.cz/cs/r-8995-aktuality-sic";

    @Override
    public List<NewsArticle> scrape() {
        try {
            org.jsoup.nodes.Document doc = Jsoup.connect(FULL_URL).get();
            Elements cards = doc.select(".article-card");
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d.M.y");

            return cards
                    .stream()
                    .map(card -> new NewsArticle(
                            null,
                            card.getElementsByClass("card-title").text(),
                            LocalDate.parse(card.getElementsByClass("card-text date text-muted").text(), dateTimeFormatter),
                            card.getElementsByClass("card-text article-perex d-none d-lg-block").text(),
                            BASE_URL + card.getElementsByClass("card-img-top").attr("src"),
                            BASE_URL + card.getElementsByClass("btn btn-default featured-btn").attr("href"),
                            WebsiteSource.KNIHOVNA))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new AlephException("Problem with content from lib.czu.cz");
        }
    }
}
