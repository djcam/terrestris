package com.terrestris.map.service.inter;

import com.terrestris.map.domain.entity.*;
import com.terrestris.map.form.ProfileForm;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by dcampbell2 on 3/4/2015.
 */
public interface ProfileService {
    Optional<Profile> getProfileByPid(long pid);
    Optional<Profile> getProfileByHandle(String handle);
    Collection<Profile> getProfilesByUid(User uid);
    Page<Profile> getProfilesByCoords(Longitude lon, Latitude lat);
    Set<ProfilePerk> saveProfilePerks(Set<ProfilePerk> profilePerks);
    ProfilePerk saveProfilePerk(ProfilePerk profilePerk);
    Profile create(ProfileForm form, User user, Longitude lon, Latitude lat);
    Profile save(Profile profile);
}
