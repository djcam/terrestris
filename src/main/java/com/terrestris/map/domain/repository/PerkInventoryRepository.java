package com.terrestris.map.domain.repository;

import com.terrestris.map.domain.entity.PerkInventory;
import com.terrestris.map.domain.entity.Profile;
import com.terrestris.map.domain.object.Perk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

/**
 * Created by dcampbell2 on 5/18/2015.
 */
public interface PerkInventoryRepository extends JpaRepository<PerkInventory, Long> {
    ArrayList<PerkInventory> findAllByPerkid(Perk perkid);
}
