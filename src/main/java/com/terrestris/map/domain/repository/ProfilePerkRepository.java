package com.terrestris.map.domain.repository;

import com.terrestris.map.domain.entity.Profile;
import com.terrestris.map.domain.entity.ProfilePerk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by dcampbell2 on 5/14/2015.
 */
public interface ProfilePerkRepository extends JpaRepository<ProfilePerk, Long> {
    List<ProfilePerk> findAllByPid(Profile pid);
}
