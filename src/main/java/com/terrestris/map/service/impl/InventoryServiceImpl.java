package com.terrestris.map.service.impl;

import com.terrestris.map.domain.entity.*;
import com.terrestris.map.domain.object.Activity;
import com.terrestris.map.domain.object.BountyName;
import com.terrestris.map.domain.object.Perk;
import com.terrestris.map.domain.repository.*;
import com.terrestris.map.service.inter.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by dcampbell2 on 4/6/2015.
 */

@Service
public class InventoryServiceImpl implements InventoryService {

    private final ProfileInventoryRepository profileInventoryRepository;
    private final ItemRepository itemRepository;
    private final ActivityLogRepository activityLogRepository;
    private final PerkInventoryRepository perkInventoryRepository;
    private final LocationInventoryRepository locInventoryRepository;

    @Autowired
    public InventoryServiceImpl(ProfileInventoryRepository profileInventoryRepository, ItemRepository itemRepository,
                                ActivityLogRepository activityLogRepository,
                                PerkInventoryRepository perkInventoryRepository,
                                LocationInventoryRepository locInventoryRepository) {
        this.profileInventoryRepository = profileInventoryRepository;
        this.itemRepository = itemRepository;
        this.activityLogRepository = activityLogRepository;
        this.perkInventoryRepository = perkInventoryRepository;
        this.locInventoryRepository = locInventoryRepository;
    }

    @Override
    public ArrayList<ProfileInventory> getAllByPid(Profile pid) {
        ArrayList<ProfileInventory> inventory = new ArrayList<>();
        inventory.addAll(profileInventoryRepository.findAllByPid(pid));
        return inventory;
    }

    @Override
    public ArrayList<LocationInventory> getAllByLid(Location lid) {
        ArrayList<LocationInventory> inventory = new ArrayList<>();
        inventory.addAll(locInventoryRepository.findAllByLid(lid));
        return inventory;
    }

    @Override
    public ArrayList<PerkInventory> getAllByPerkid(Perk perkid) {
        return perkInventoryRepository.findAllByPerkid(perkid);
    }

    @Override
    public Optional<ProfileInventory> getOneByPidAndIid(Profile pid, Item iid) {
        return profileInventoryRepository.findOneByPidAndIid(pid, iid);
    }

    @Override
    public Optional<LocationInventory> getOneByLidAndIid(Location lid, Item iid) {
        return locInventoryRepository.findOneByLidAndIid(lid, iid);
    }

    @Override
    public HashMap<Long, ProfileInventory> getAllByPidAndIids(Profile pid, Iterable<Item> iids) {
        ArrayList<ProfileInventory> invList = profileInventoryRepository.findAllByPidAndIidIn(pid, iids);
        HashMap<Long, ProfileInventory> invMap = new HashMap<>();
        for (ProfileInventory pinv : invList) {
            invMap.put(pinv.getIid().getIid(), pinv);
        }
        return invMap;
    }

    @Override
    public ProfileInventory getPOneByInvid(long invid) {
        return profileInventoryRepository.findOne(invid);
    }

    @Override
    public LocationInventory getLOneByInvid(long invid) {
        return locInventoryRepository.findOne(invid);
    }

    @Override
    public List<LocationInventory> getLAllByInvids(Iterable<Long> invids) {
        return locInventoryRepository.findAll(invids);
    }

    @Override
    public List<ProfileInventory> getPAllByInvids(Iterable<Long> invids) {
        return profileInventoryRepository.findAll(invids);
    }

    @Override
    public List<PerkInventory> getProfilePerks(List<ProfileInventory> inventories, Perk perkid) {
        ArrayList<PerkInventory> offerings = getAllByPerkid(perkid);
        for (PerkInventory offering : offerings) {
            boolean hasItem = false;
            for (ProfileInventory inventory : inventories) {
                if (inventory.getIid().getIid() == offering.getIid().getIid()) {
                    offering.setProfileCount(inventory.getCount());
                    if (inventory.getCount() >= offering.getCount()) {
                        offering.setHasItem(true);
                    }
                }
            }
        }
        return offerings;
    }

    @Override
    public ProfileInventory addItem(Profile pid, long iid, int count) {
        if (count > 0) {
            Item item = itemRepository.findOne(iid);
            Optional<ProfileInventory> piOpt = getOneByPidAndIid(pid, item);
            ProfileInventory pi = piOpt.orElse(new ProfileInventory(pid, item, 0));
            pi.add(count);
            return save(pi);
        }
        return null;
    }

    @Override
    public ProfileInventory createRandom(Profile pid) {
        Random r = new Random();
        ArrayList<Item> items = new ArrayList<>();
        items.addAll(itemRepository.findAllByDropOrderByValueAsc(1));

        boolean rare1 = r.nextBoolean(), rare2 = r.nextBoolean(), rare3 = r.nextBoolean();
        int index;
        if (rare1 && rare2 && rare3) {
            index = r.nextInt(items.size());
        } else if (rare1 && rare2) {
            index = r.nextInt(items.size() / 2);
        } else {
            index = r.nextInt(items.size() / 4);
        }

        int count = (rare1 ? 1 : (rare2 ? 2 : 0));
        if (count < 1) return null;

        ProfileInventory inventory = getOneByPidAndIid(pid, items.get(index))
                .orElse(new ProfileInventory(pid, items.get(index), 0));
        inventory.add(count);

        ActivityLog activityLog = new ActivityLog();
        activityLog.setUid(pid.getUid());
        activityLog.setPid(pid);
        activityLog.setIid(inventory.getIid());
        activityLog.setCount(count);
        activityLog.setAid(Activity.FINDS);
        activityLog.setXpos(pid.getXpos());
        activityLog.setYpos(pid.getYpos());
        activityLogRepository.save(activityLog);

        return save(inventory);
    }


    @Override
    public ProfileInventory save(ProfileInventory inventory) {
        return profileInventoryRepository.save(inventory);
    }

    @Override
    public List<ProfileInventory> saveAll(List<ProfileInventory> inventories) {
        return profileInventoryRepository.save(inventories);
    }

    @Override
    public void delete(ProfileInventory inventory) {
        profileInventoryRepository.delete(inventory);
    }
}
