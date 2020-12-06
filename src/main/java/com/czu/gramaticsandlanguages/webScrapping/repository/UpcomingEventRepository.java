package com.czu.gramaticsandlanguages.webScrapping.repository;

import com.appventure.AppBackend.webScrapping.model.UpcomingEvent;
import com.appventure.AppBackend.webScrapping.model.WebsiteSource;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpcomingEventRepository extends CrudRepository<UpcomingEvent, Integer> {

    Iterable<UpcomingEvent> findByWebsiteSource(WebsiteSource source);

}
