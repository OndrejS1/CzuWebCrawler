package com.czu.gramaticsandlanguages.webScrapping.service;

import com.appventure.AppBackend.aleph.exception.AlephException;
import com.appventure.AppBackend.webScrapping.model.PefEventsResources;
import com.appventure.AppBackend.webScrapping.model.UpcomingEvent;
import com.appventure.AppBackend.webScrapping.model.WebsiteSource;
import com.appventure.AppBackend.webScrapping.repository.UpcomingEventRepository;
import com.appventure.AppBackend.webScrapping.translator.UpcomingEventsTranslator;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class UpcomingEventsService {

    private static final String CZU_BASE_URL = "https://wp.v2.czu.cz";
    private static final String CZU_FULL_URL = "https://wp.v2.czu.cz/cs/r-8544-akce?fbclid=IwAR0dknrtFRAyIiYtuXrb68bOz_jIlHE0r7fThrKLGn6D8GUjqnlaj4QDfIE";
    private static final String PEF_FULL_URL = "http://akce.pef.czu.cz/api/events";

    private final UpcomingEventRepository upcomingEventRepository;

    @Scheduled(cron = "0 */12 * * *")
    public void saveUpcomingEvents() {
        List<UpcomingEvent> czuEvents = getCzuEvents();
        List<UpcomingEvent> pefEvents = getPefEvents();

        List<UpcomingEvent> upcomingEvents = Stream.concat(czuEvents.stream(), pefEvents.stream())
                .collect(Collectors.toList());

        upcomingEventRepository.saveAll(upcomingEvents);
    }

    public List<UpcomingEvent> getPefEvents() {

        RestTemplate restTemplate = new RestTemplate();
        PefEventsResources resp = restTemplate.getForObject(PEF_FULL_URL, PefEventsResources.class);

        return  resp.getData()
                .stream()
                .filter(Objects::nonNull)
                .map(UpcomingEventsTranslator::translate)
                .collect(Collectors.toList());
    }

    public List<UpcomingEvent> getCzuEvents() {
        try {
            org.jsoup.nodes.Document doc = Jsoup.connect(CZU_FULL_URL).get();
            Elements cards = doc.select("body > section > table > tbody > tr");

            List<UpcomingEvent> results = new ArrayList<>();

            for (Element row : cards) {
                if(!row.text().contains("Datum")) {
                    String dateResult = row.select("td").get(0).text();
                    String textResult  = row.select("td").get(1).text();
                    String linkResult = row.select("a").get(1).attr("href");

                    results.add(new UpcomingEvent(
                            null,
                            textResult,
                            parseWithDefaultYear(dateResult),
                            null,
                            CZU_BASE_URL + linkResult,
                            WebsiteSource.CZUWP));
                }
            }
            return results;
        } catch (IOException e) {
            throw new AlephException("Problem with content from lib.czu.cz");
        }
    }

    public List<UpcomingEvent> getUpcomingEvents(WebsiteSource source) {
        if(source != null) {
            return processUpcomingEvents(upcomingEventRepository.findByWebsiteSource(source));
        } else {
            return processUpcomingEvents(upcomingEventRepository.findAll());
        }
    }

    private List<UpcomingEvent> processUpcomingEvents(Iterable<UpcomingEvent> all) {
        return StreamSupport
                .stream(all.spliterator(), false)
                .sorted(Comparator.comparing(UpcomingEvent::getDate, Collections.reverseOrder()))
                .collect(Collectors.toList());
    }

    private static Date parseWithDefaultYear(String stringWithoutYear) {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        ZoneId defaultZoneId = ZoneId.systemDefault();

        DateTimeFormatter parseFormatter = new DateTimeFormatterBuilder()
                .appendPattern("d.M.")
                .parseDefaulting(ChronoField.YEAR, year)
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                .toFormatter(Locale.ENGLISH);

        LocalDateTime dateTime = LocalDateTime.parse(stringWithoutYear, parseFormatter);

        return Date.from(dateTime
                .toLocalDate()
                .atStartOfDay(defaultZoneId)
                .toInstant());
    }
}
