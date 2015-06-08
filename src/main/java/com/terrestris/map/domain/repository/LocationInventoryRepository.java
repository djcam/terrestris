package com.terrestris.map.domain.repository;

import com.terrestris.map.domain.entity.Item;
import com.terrestris.map.domain.entity.Location;
import com.terrestris.map.domain.entity.LocationInventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by dcampbell2 on 4/10/2015.
 */
public interface LocationInventoryRepository extends JpaRepository<LocationInventory, Long>{
    Collection<LocationInventory> findAllByLid(Location lid);
    Optional<LocationInventory> findOneByLidAndIid(Location lid, Item iid);
}
