package com.terrestris.map.domain.repository;

/**
 * Created by dcampbell2 on 2/25/2015.
 */

import com.mysema.query.types.Predicate;
import com.terrestris.map.domain.entity.Latitude;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.util.Collection;
import java.util.Optional;

public interface LatitudeRepository extends JpaRepository<Latitude, Long>, QueryDslPredicateExecutor<Latitude> {
    Optional<Latitude> findOneByRname(String rname);
    Latitude findFirstByOrderByRidAsc();
    Latitude findFirstByOrderByRidDesc();
}
