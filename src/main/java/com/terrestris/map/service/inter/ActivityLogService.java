package com.terrestris.map.service.inter;

import com.terrestris.map.domain.entity.ActivityLog;
import com.terrestris.map.domain.entity.Item;
import com.terrestris.map.domain.entity.Profile;
import com.terrestris.map.domain.entity.User;
import com.terrestris.map.domain.object.Activity;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by dcampbell2 on 3/24/2015.
 */
public interface ActivityLogService {
    List<ActivityLog> getAllActivityLog();
    Collection<ActivityLog> getAllActivityLogByPid(Profile pid);
    Collection<ActivityLog> getAllActivityLogByUid(User uid);

    Page<ActivityLog> getActivityLogByPid(int start, int pageSize, Profile pid);
    Page<ActivityLog> getActivityLogByUid(int start, int pageSize, User uid);
    Page<ActivityLog> getAllActivityLogPageable(int start, int pageSize);
    ActivityLog create(ActivityLog activityLog);
    ActivityLog create(Profile profile, Profile otherProfile, Activity activity, Item item, int count);
    Collection<ActivityLog> createAll(ArrayList<ActivityLog> logs);
}
