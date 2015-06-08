package com.terrestris.map.domain.repository;

/**
 * Created by dcampbell2 on 2/25/2015.
 */

import com.terrestris.map.domain.entity.Longitude;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import com.mysema.query.types.Predicate;

import java.util.Collection;
import java.util.Optional;

public interface LongitudeRepository extends JpaRepository<Longitude, Long>, QueryDslPredicateExecutor<Longitude> {
    Optional<Longitude> findOneByRname(String rname);
    Longitude findFirstByOrderByRidAsc();
    Longitude findFirstByOrderByRidDesc();
}
