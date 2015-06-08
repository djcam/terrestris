package com.terrestris.map.service.impl;

import com.terrestris.map.domain.entity.*;
import com.terrestris.map.domain.object.Activity;
import com.terrestris.map.domain.repository.ActivityLogRepository;
import com.terrestris.map.service.CurrentUser;
import com.terrestris.map.service.inter.ActivityLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by dcampbell2 on 3/24/2015.
 */

@Service
public class ActivityLogServiceImpl implements ActivityLogService {

    private final ActivityLogRepository activityLogRepository;

    @Autowired
    public ActivityLogServiceImpl(ActivityLogRepository activityLogRepository) {
        this.activityLogRepository = activityLogRepository;
    }

    @Override
    public List<ActivityLog> getAllActivityLog() {
        return activityLogRepository.findAll();
    }

    @Override
    public Collection<ActivityLog> getAllActivityLogByPid(Profile pid) {
        return activityLogRepository.findAllByPid(pid);
    }

    @Override
    public Collection<ActivityLog> getAllActivityLogByUid(User uid) {
        return activityLogRepository.findAllByUid(uid);
    }

    @Override
    public Page<ActivityLog> getActivityLogByPid(int start, int pageSize, Profile pid) {
        return activityLogRepository.findByPid(pid, new PageRequest(start, pageSize, Sort.Direction.DESC, "atstamp"));
    }

    @Override
    public Page<ActivityLog> getActivityLogByUid(int start, int pageSize, User uid) {
        return activityLogRepository.findByUid(uid, new PageRequest(start, pageSize, Sort.Direction.DESC, "atstamp"));
    }

    @Override
    public Page<ActivityLog> getAllActivityLogPageable(int start, int pageSize) {
        return activityLogRepository.findAll(new PageRequest(start, pageSize, Sort.Direction.DESC, "atstamp"));
    }


    @Override
    public ActivityLog create(ActivityLog activityLog) {
        return activityLogRepository.save(activityLog);
    }

    @Override
    public ActivityLog create(Profile profile, Profile otherProfile, Activity activity, Item item, int count) {
        ActivityLog log = new ActivityLog(profile.getUid(), profile, profile.getYpos(), profile.getXpos());
        log.setAid(activity);
        log.setIid(item);
        log.setOtherPid(otherProfile);
        log.setCount(count);
        return activityLogRepository.save(log);
    }

    @Override
    public Collection<ActivityLog> createAll(ArrayList<ActivityLog> logs) {
        return activityLogRepository.save(logs);
    }
}
