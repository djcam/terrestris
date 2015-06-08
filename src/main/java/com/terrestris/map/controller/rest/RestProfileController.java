package com.terrestris.map.controller.rest;

import com.terrestris.map.domain.object.Activity;
import com.terrestris.map.domain.entity.ActivityLog;
import com.terrestris.map.domain.entity.Profile;
import com.terrestris.map.domain.entity.ProfileStat;
import com.terrestris.map.domain.object.Stat;
import com.terrestris.map.service.CurrentUser;
import com.terrestris.map.service.inter.ActivityLogService;
import com.terrestris.map.service.inter.ProfileService;
import com.terrestris.map.service.inter.RoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dcampbell2 on 3/30/2015.
 */

@RestController
public class RestProfileController {

    private final ProfileService profileService;
    private final RoadService roadService;
    private final ActivityLogService activityLogService;

    @Autowired
    public RestProfileController(ProfileService profileService, RoadService roadService,
                                 ActivityLogService activityLogService) {
        this.profileService = profileService;
        this.roadService = roadService;
        this.activityLogService = activityLogService;
    }

    @RequestMapping("/api/users")
    public Page<Profile> getUsersPage(@RequestParam(value = "lat") long lat, @RequestParam(value="lon") long lon,
                                   RedirectAttributes ra) {
        return profileService.getProfilesByCoords(roadService.getLongitudeByRid(lon).get(), roadService.getLatitudeByRid(lat).get());
    }

    @RequestMapping("/api/profile/stats")
    public RestProfileData refillStat(@RequestParam(value = "stat") String stat, @RequestParam(value = "type") int type,
                                                 Authentication auth) {
        Profile profile = ((CurrentUser)auth.getPrincipal()).getProfile();
        ArrayList<ActivityLog> logs = new ArrayList<>();
        ActivityLog replenished;
        for (ProfileStat pstat : profile.getProfileStats()) {
            if (pstat.getStid().getTitle().toLowerCase().equals(stat.toLowerCase())) {
                int diff = (type == 0 ? pstat.refillStat(true) : pstat.refillStat(false));
                if (diff > 0) {
                    replenished = new ActivityLog(profile.getUid(), profile);
                    replenished.setAid(Activity.REPLENISHES);
                    replenished.setCount(diff);
                    logs.add(activityLogService.create(replenished));
                }
            }
        }

        profileService.save(profile);
        return new RestProfileData(profile.getStatMap(), logs);
    }

    public class RestProfileData {

        private HashMap<Stat, ProfileStat> statMap;
        private ArrayList<ActivityLog> activityLog;

        public RestProfileData (HashMap<Stat, ProfileStat> statMap, ArrayList<ActivityLog> activityLog) {
            this.statMap = statMap;
            this.activityLog = activityLog;
        }

        public HashMap<Stat, ProfileStat> getStatMap() {
            return statMap;
        }

        public void setStatMap(HashMap<Stat, ProfileStat> statMap) {
            this.statMap = statMap;
        }

        public ArrayList<ActivityLog> getActivityLog() {
            return activityLog;
        }

        public void setActivityLog(ArrayList<ActivityLog> activityLog) {
            this.activityLog = activityLog;
        }
    }
}
