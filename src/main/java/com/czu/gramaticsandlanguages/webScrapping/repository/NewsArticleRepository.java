package com.czu.gramaticsandlanguages.webScrapping.repository;

import com.appventure.AppBackend.webScrapping.model.NewsArticle;
import com.appventure.AppBackend.webScrapping.model.WebsiteSource;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsArticleRepository extends CrudRepository<NewsArticle, Long> {

    Iterable<NewsArticle> findByWebsiteSource(WebsiteSource source);

}
