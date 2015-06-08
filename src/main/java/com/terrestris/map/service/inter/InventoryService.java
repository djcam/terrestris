package com.terrestris.map.service.inter;

import com.terrestris.map.domain.entity.*;
import com.terrestris.map.domain.object.Perk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * Created by dcampbell2 on 4/6/2015.
 */
public interface InventoryService {
    ArrayList<ProfileInventory> getAllByPid(Profile pid);
    ArrayList<LocationInventory> getAllByLid(Location lid);
    ArrayList<PerkInventory> getAllByPerkid(Perk perkid);

    Optional<ProfileInventory> getOneByPidAndIid(Profile pid, Item iid);
    Optional<LocationInventory> getOneByLidAndIid(Location lid, Item iid);
    HashMap<Long, ProfileInventory> getAllByPidAndIids(Profile pid, Iterable<Item> iids);

    ProfileInventory getPOneByInvid(long invid);
    LocationInventory getLOneByInvid(long invid);
    List<LocationInventory> getLAllByInvids(Iterable<Long> invids);
    List<ProfileInventory> getPAllByInvids(Iterable<Long> invids);

    List<PerkInventory> getProfilePerks(List<ProfileInventory> inventories, Perk perkid);
    ProfileInventory addItem(Profile pid, long iid, int count);
    ProfileInventory createRandom(Profile pid);
    ProfileInventory save(ProfileInventory inventory);
    List<ProfileInventory> saveAll(List<ProfileInventory> inventories);
    void delete(ProfileInventory inventory);
}
