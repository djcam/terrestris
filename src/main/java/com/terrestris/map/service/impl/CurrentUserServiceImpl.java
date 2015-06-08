package com.terrestris.map.service.impl;

import com.terrestris.map.domain.object.Role;
import com.terrestris.map.service.CurrentUser;
import com.terrestris.map.service.inter.CurrentUserService;
import org.springframework.stereotype.Service;

/**
 * Created by dcampbell2 on 2/26/2015.
 */

@Service
public class CurrentUserServiceImpl implements CurrentUserService {

    @Override
    public boolean canAccessUser(CurrentUser currentUser, Long userId) {
        return currentUser != null
                && (currentUser.getRole() == Role.ADMIN || currentUser.getId().equals(userId));
    }

    @Override
    public boolean canAccessProfile(CurrentUser currentUser, Long profileId) {
        return currentUser != null &&
                profileId > 0;
    }

}
