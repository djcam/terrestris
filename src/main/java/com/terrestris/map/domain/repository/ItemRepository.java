package com.terrestris.map.domain.repository;

import com.terrestris.map.domain.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

/**
 * Created by dcampbell2 on 4/6/2015.
 */
public interface ItemRepository extends JpaRepository<Item, Long> {
    Collection<Item> findAllByDropOrderByValueAsc(int drop);
}
