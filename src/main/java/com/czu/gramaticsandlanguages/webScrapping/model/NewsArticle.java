package com.czu.gramaticsandlanguages.webScrapping.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class NewsArticle implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private LocalDate date;
    private String text;
    private String imgSrc;
    private String url;

    @Enumerated(EnumType.STRING)
    private WebsiteSource websiteSource;
}
