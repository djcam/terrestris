package com.terrestris.map.service.impl;

import com.terrestris.map.domain.entity.*;
import com.terrestris.map.domain.object.RpgClass;
import com.terrestris.map.domain.object.Stat;
import com.terrestris.map.domain.repository.ProfilePerkRepository;
import com.terrestris.map.domain.repository.ProfileRepository;
import com.terrestris.map.domain.repository.ProfileStatRepository;
import com.terrestris.map.form.ProfileForm;
import com.terrestris.map.service.inter.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.terrestris.map.domain.predicate.ProfilePredicates.profileOnSquare;


/**
 * Created by dcampbell2 on 3/4/2015.
 */

@Service
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileStatRepository profileStatRepository;
    private final ProfilePerkRepository profilePerkRepository;

    @Autowired
    ProfileServiceImpl(ProfileRepository profileRepository, ProfileStatRepository profileStatRepository,
                       ProfilePerkRepository profilePerkRepository) {
        this.profileRepository = profileRepository;
        this.profileStatRepository = profileStatRepository;
        this.profilePerkRepository = profilePerkRepository;
    }

    @Override
    public Optional<Profile> getProfileByPid(long pid) {
        return Optional.ofNullable(profileRepository.findOne(pid));
    }

    @Override
    public Optional<Profile> getProfileByHandle(String handle) {
        return profileRepository.findOneByHandle(handle);
    }

    @Override
    public Collection<Profile> getProfilesByUid(User uid) {
        return profileRepository.findAllByUid(uid);
    }

    @Override
    public Page<Profile> getProfilesByCoords(Longitude lon, Latitude lat) {
        return profileRepository.findAll(profileOnSquare(lon, lat),
                new PageRequest(0, 10, Sort.Direction.ASC, "handle"));
    }

    @Override
    public Set<ProfilePerk> saveProfilePerks(Set<ProfilePerk> profilePerks) {
        Set<ProfilePerk> savedPerks = new HashSet<>(profilePerkRepository.save(profilePerks));
        return savedPerks;
    }

    @Override
    public ProfilePerk saveProfilePerk(ProfilePerk profilePerk) {
        return profilePerkRepository.save(profilePerk);
    }

    @Override
    public Profile create(ProfileForm form, User user, Longitude lon, Latitude lat) {
        Profile profile = new Profile();
        profile.setLevel(Integer.parseInt(form.getLevel()));
        profile.setHandle(form.getHandle());
        profile.setXpos(lon);
        profile.setYpos(lat);
        profile.setRpgcid(RpgClass.valueOf(form.getRpgcid()));
        profile.setUid(user);
        profile = profileRepository.save(profile);

        Set<ProfileStat> profileStats = new HashSet<>();
        profileStats.add(new ProfileStat(0, 100, 0, 5, profile, Stat.MANA));
        profileStats.add(new ProfileStat(100, 100, 100, 5, profile, Stat.STAMINA));
        profileStats.add(new ProfileStat(0, 100, 0, 0, profile, Stat.EXPERIENCE));

        Set<ProfileStat> savedStats = new HashSet<>(profileStatRepository.save(profileStats));
        profile.setProfileStats(savedStats);

        return profile;
    }

    @Override
    public Profile save(Profile profile) {
        profileStatRepository.save(profile.flushStats());
        return profileRepository.save(profile);
    }


}
