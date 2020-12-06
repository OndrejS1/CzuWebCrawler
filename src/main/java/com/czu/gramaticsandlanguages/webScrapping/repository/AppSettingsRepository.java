package com.czu.gramaticsandlanguages.webScrapping.repository;

import com.appventure.AppBackend.appsettings.model.AppSettings;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppSettingsRepository extends CrudRepository<AppSettings, Integer> {

}
