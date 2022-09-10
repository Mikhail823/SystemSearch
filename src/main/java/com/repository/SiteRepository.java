package com.repository;

import com.model.Site;
import org.springframework.data.repository.CrudRepository;

public interface SiteRepository extends CrudRepository<Site, Integer> {
    Site findByUrl (String url);
}
