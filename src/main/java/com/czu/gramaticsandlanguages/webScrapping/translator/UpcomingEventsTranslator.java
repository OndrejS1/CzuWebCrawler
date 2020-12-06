package com.czu.gramaticsandlanguages.webScrapping.translator;

import com.appventure.AppBackend.webScrapping.model.PefEventsResource;
import com.appventure.AppBackend.webScrapping.model.UpcomingEvent;
import com.appventure.AppBackend.webScrapping.model.WebsiteSource;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class UpcomingEventsTranslator {


    public static UpcomingEvent translate(PefEventsResource resource) {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Date date = Date.from(LocalDate.parse(resource.getStarts_at(), dtf).atStartOfDay(defaultZoneId).toInstant());

        return new UpcomingEvent(resource.getId(), resource.getTitle(), date, resource.getDescr(), null, WebsiteSource.PEF);
    }
}
