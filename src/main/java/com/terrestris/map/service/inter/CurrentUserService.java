package com.terrestris.map.service.inter;

import com.terrestris.map.service.CurrentUser;

/**
 * Created by dcampbell2 on 2/26/2015.
 */
public interface CurrentUserService {
    boolean canAccessUser(CurrentUser currentUser, Long userId);
    boolean canAccessProfile(CurrentUser currentUser, Long profileId);
}
