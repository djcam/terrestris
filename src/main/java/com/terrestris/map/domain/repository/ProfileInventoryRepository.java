package com.terrestris.map.domain.repository;

import com.terrestris.map.domain.entity.Item;
import com.terrestris.map.domain.entity.Profile;
import com.terrestris.map.domain.entity.ProfileInventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * Created by dcampbell2 on 4/6/2015.
 */
public interface ProfileInventoryRepository extends JpaRepository<ProfileInventory, Long> {
    Collection<ProfileInventory> findAllByPid(Profile pid);
    Optional<ProfileInventory> findOneByPidAndIid(Profile pid, Item iid);
    ArrayList<ProfileInventory> findAllByPidAndIidIn(Profile pid, Iterable<Item> iids);
}
