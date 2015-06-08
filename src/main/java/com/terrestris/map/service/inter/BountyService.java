package com.terrestris.map.service.inter;

import com.terrestris.map.domain.entity.*;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by dcampbell2 on 4/1/2015.
 */
public interface BountyService {
    final static int MAX_BOUNTIES = 4;

    Optional<Bounty> getBountyByBid(long bid);
    Collection<Bounty> getAllBountiesByPidAndCoords(Profile pid, Longitude lon, Latitude lat);
    Collection<Bounty> getAllBountiesByPid(Profile pid);
    Collection<Bounty> getAllBountiesByCoords(Longitude lon, Latitude lat);
    Collection<Bounty> getAllBountiesByLid(Location lid);
    Collection<Bounty> getAllBountiesByLidAndPid(Location lid, Profile pid);
    Collection<Bounty> getAllBounties();
    Collection<Bounty> create(Profile profile, Location location);
    Collection<Bounty> saveAll(Collection<Bounty> bounties);
    Bounty save(Bounty bounty);
}
