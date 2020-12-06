package com.czu.gramaticsandlanguages.webScrapping.model;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class UpcomingEvent implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    private String title;
    private Date date;
    private String description;

    @Nullable
    private String link;

    @Enumerated(EnumType.STRING)
    private WebsiteSource websiteSource;

}
