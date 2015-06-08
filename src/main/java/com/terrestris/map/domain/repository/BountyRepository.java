package com.terrestris.map.domain.repository;

import com.terrestris.map.domain.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

/**
 * Created by dcampbell2 on 4/1/2015.
 */
public interface BountyRepository extends JpaRepository<Bounty, Long> {
    Collection<Bounty> findAllByPidAndXposAndYpos(Profile pid, Longitude xpos, Latitude ypos);
    Collection<Bounty> findAllByXposAndYpos(Longitude xpos, Latitude ypos);
    Collection<Bounty> findAllByPid(Profile pid);
    Collection<Bounty> findAllByLid(Location lid);
    Collection<Bounty> findAllByLidAndPid(Location lid, Profile pid);
}
