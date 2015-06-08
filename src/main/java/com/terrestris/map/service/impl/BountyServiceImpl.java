package com.terrestris.map.service.impl;

import com.terrestris.map.domain.entity.*;
import com.terrestris.map.domain.object.BountyName;
import com.terrestris.map.domain.object.Stat;
import com.terrestris.map.domain.repository.BountyRepository;
import com.terrestris.map.domain.repository.LatitudeRepository;
import com.terrestris.map.domain.repository.LocationRepository;
import com.terrestris.map.domain.repository.LongitudeRepository;
import com.terrestris.map.service.inter.BountyService;
import com.terrestris.map.service.inter.RoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by dcampbell2 on 4/1/2015.
 */

@Service
public class BountyServiceImpl implements BountyService {

    private final BountyRepository bountyRepository;
    private final RoadService roadService;

    @Autowired
    public BountyServiceImpl(BountyRepository bountyRepository, RoadService roadService) {
        this.bountyRepository = bountyRepository;
        this.roadService = roadService;
    }

    @Override
    public Optional<Bounty> getBountyByBid(long bid) {
        return Optional.ofNullable(bountyRepository.findOne(bid));
    }

    @Override
    public Collection<Bounty> getAllBountiesByPidAndCoords(Profile pid, Longitude lon, Latitude lat) {
        return bountyRepository.findAllByPidAndXposAndYpos(pid, lon, lat);
    }

    @Override
    public Collection<Bounty> getAllBountiesByPid(Profile pid) {
        return bountyRepository.findAllByPid(pid);
    }

    @Override
    public Collection<Bounty> getAllBountiesByCoords(Longitude lon, Latitude lat) {
        return bountyRepository.findAllByXposAndYpos(lon, lat);
    }

    @Override
    public Collection<Bounty> getAllBountiesByLid(Location lid) {
        return bountyRepository.findAllByLid(lid);
    }

    @Override
    public Collection<Bounty> getAllBountiesByLidAndPid(Location lid, Profile pid) {
        return bountyRepository.findAllByLidAndPid(lid, pid);
    }

    @Override
    public Collection<Bounty> getAllBounties() {
        return bountyRepository.findAll();
    }

    @Override
    public Collection<Bounty> create(Profile profile, Location location) {
        int removed = 0, total = 0;
        for (Bounty b : bountyRepository.findAllByPid(profile)) {
            if (b.getStatus() == 3) {
                bountyRepository.delete(b);
                removed++;
            }
            total++;
        }

        int toAdd = MAX_BOUNTIES - total + removed;
        Collection<Bounty> bounties = new ArrayList<>();
        if (toAdd > 0) {
            Random r = new Random();
            BountyName[] bountyNames = BountyName.values();
            for (int i = 0; i < toAdd; i++) {
                Bounty bounty = new Bounty();
                bounty.setLid(location);
                bounty.setXpos(roadService.getRandomLongitude());
                bounty.setYpos(roadService.getRandomLatitude());
                bounty.setPid(profile);
                bounty.setBname(bountyNames[r.nextInt(bountyNames.length)].getBname());
                bounty.setEvasion(r.nextInt(100) + 1);
                bounty.setValue(profile.getLevel() * (r.nextInt(90) + 10));
                bounty.setXp(profile.getLevel() * (r.nextInt(90) + 10) / 20);
                bounty.setMaxsp(profile.getStat(Stat.STAMINA).getMax());
                bounty.setValue(bounty.getMaxsp());
                bounty.setMvcost((bounty.getMaxsp() / 20));
                bounties.add(bounty);
            }
            return saveAll(bounties);
        }

        return bounties;
    }

    @Override
    public Collection<Bounty> saveAll(Collection<Bounty> bounties) {
        return bountyRepository.save(bounties);
    }

    @Override
    public Bounty save(Bounty bounty) {
        return bountyRepository.save(bounty);
    }
}
