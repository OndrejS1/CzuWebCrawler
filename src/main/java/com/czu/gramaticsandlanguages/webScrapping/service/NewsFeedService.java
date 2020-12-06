package com.czu.gramaticsandlanguages.webScrapping.service;

import com.appventure.AppBackend.webScrapping.model.NewsArticle;
import com.appventure.AppBackend.webScrapping.model.WebsiteSource;
import com.appventure.AppBackend.webScrapping.repository.NewsArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class NewsFeedService {

    private final NewsArticleRepository newsArticleRepository;
    private final PefScrappingService pefScrappingService;
    private final IzunScrappingService izunScrappingService;
    private final AlephScrappingService alephScrappingService;

    @Scheduled(cron = "0 */12 * * *")
    public void updateNewsFeed() {
        CompletableFuture<List<NewsArticle>> pefNews
                = CompletableFuture.supplyAsync(pefScrappingService::scrape);

        CompletableFuture<List<NewsArticle>> alephNews
                = CompletableFuture.supplyAsync(alephScrappingService::scrape);

        CompletableFuture<List<NewsArticle>> izunNews
                = CompletableFuture.supplyAsync(izunScrappingService::scrape);

         List<NewsArticle> results = Stream.of(pefNews, alephNews, izunNews)
                .map(CompletableFuture::join)
                .flatMap(List::stream)
                .sorted(Comparator.comparing(NewsArticle::getDate, Collections.reverseOrder()))
                .collect(Collectors.toList());

         newsArticleRepository.deleteAll();
         newsArticleRepository.saveAll(results);
    }

    public List<NewsArticle> getFeed(WebsiteSource source) {
        if(source != null) {
            return processFeed(newsArticleRepository.findByWebsiteSource(source));
        } else {
            return processFeed(newsArticleRepository.findAll());
        }
    }

    private List<NewsArticle> processFeed(Iterable<NewsArticle> byWebsiteSource) {
        return StreamSupport
                .stream(byWebsiteSource.spliterator(), false)
                .sorted(Comparator.comparing(NewsArticle::getDate, Collections.reverseOrder()))
                .collect(Collectors.toList());
    }

    public List<NewsArticle> getLastFiveFeed(WebsiteSource source) {

        if(source != null) {
            return StreamSupport
                    .stream(newsArticleRepository.findByWebsiteSource(source).spliterator(), false)
                    .sorted(Comparator.comparing(NewsArticle::getDate, Collections.reverseOrder()))
                    .limit(5)
                    .collect(Collectors.toList());
        } else {
            return StreamSupport
                    .stream(newsArticleRepository.findAll().spliterator(), false)
                    .sorted(Comparator.comparing(NewsArticle::getDate, Collections.reverseOrder()))
                    .limit(5)
                    .collect(Collectors.toList());
        }
    }
}
