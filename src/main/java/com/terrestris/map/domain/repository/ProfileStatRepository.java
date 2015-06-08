package com.terrestris.map.domain.repository;

import com.terrestris.map.domain.entity.Profile;
import com.terrestris.map.domain.entity.ProfileStat;
import com.terrestris.map.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

/**
 * Created by dcampbell2 on 4/22/2015.
 */
public interface ProfileStatRepository extends JpaRepository<ProfileStat, Long> {
    Collection<ProfileStat> findAllByPid(Profile pid);
}
