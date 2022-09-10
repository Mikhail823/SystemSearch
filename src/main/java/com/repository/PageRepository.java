package com.repository;

import com.model.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PageRepository extends CrudRepository<Page, Integer> {
    Page findByPath (String path);
    Optional<Page> findByIdAndSiteId (int id, int siteId);

    @Query(value = "SELECT count(*) from Page where site_id = :id")
    long count(@Param("id") int id);
}
