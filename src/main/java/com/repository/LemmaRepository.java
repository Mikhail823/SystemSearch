package com.repository;

import com.model.Lemma;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LemmaRepository extends CrudRepository<Lemma, Integer> {
    List<Lemma> findByLemma (String lemma);

    @Query(value = "SELECT * from search_lemma WHERE id IN(:id)", nativeQuery = true)
    List<Lemma> findById (int[] id);

    @Query(value = "SELECT count(*) from Lemma where site_id = :id")
    long count(@Param("id") long id);
}
