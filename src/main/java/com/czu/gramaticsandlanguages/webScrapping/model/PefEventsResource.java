package com.czu.gramaticsandlanguages.webScrapping.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PefEventsResource {

    private Integer id;
    private String title;
    private String descr;
    private String starts_at;

}
