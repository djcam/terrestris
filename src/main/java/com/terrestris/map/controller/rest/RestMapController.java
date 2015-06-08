package com.terrestris.map.controller.rest;

import com.terrestris.map.domain.entity.*;
import com.terrestris.map.domain.object.Activity;
import com.terrestris.map.domain.object.Stat;
import com.terrestris.map.service.inter.*;
import com.terrestris.map.service.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by dcampbell2 on 3/9/2015.
 */

@RestController
public class RestMapController {

    private final RoadService roadService;
    private final ProfileService profileService;
    private final ActivityLogService activityLogService;
    private final BountyService bountyService;
    private final InventoryService inventoryService;
    private final LocationService locationService;

    @Autowired
    public RestMapController(RoadService roadService, ProfileService profileService,
                             ActivityLogService activityLogService, BountyService bountyService,
                             InventoryService inventoryService, LocationService locationService) {
        this.roadService = roadService;
        this.profileService = profileService;
        this.activityLogService = activityLogService;
        this.bountyService = bountyService;
        this.inventoryService = inventoryService;
        this.locationService = locationService;
    }

    @RequestMapping("/api/map")
    public RestMapData getMap(@RequestParam(value = "lat") int lat, @RequestParam(value="lon") int lon,
                                Authentication authentication) {
        CurrentUser user = (CurrentUser)authentication.getPrincipal();
        Profile profile = user.getProfile();
        ArrayList<String> errors = new ArrayList<>();

        if (profile.getStatValue(Stat.STAMINA) >= profile.getMvcost()) {
            profile.setYpos(roadService.getLatitudeByRid(lat).get());
            profile.setXpos(roadService.getLongitudeByRid(lon).get());

            ProfileStat sp = profile.getStat(Stat.STAMINA);
            sp.setValue(Math.max(sp.getValue() - profile.getMvcost(), 0));
            user.setProfile(profileService.save(profile));

            activityLogService.create(profile, null, Activity.MOVES, null, 1);
            inventoryService.createRandom(profile);
        } else {
            errors.add("Your stamina is depleted.");
        }

        Collection<Bounty> bounties = bountyService.getAllBountiesByPidAndCoords(profile, profile.getXpos(), profile.getYpos());
        Optional<Location> location = locationService.getLocationByCoords(profile.getXpos(), profile.getYpos());
        RestMapData rmd = new RestMapData(
                roadService.getLatitudesGrid(profile.getYpos()),
                roadService.getLongitudesGrid(profile.getXpos()),
                profile,
                location.orElse(null),
                bounties
        );
        rmd.setUserProfiles(profileService.getProfilesByCoords(profile.getXpos(), profile.getYpos()));
        rmd.setActivityLog(activityLogService.getActivityLogByPid(0, 5, profile));
        rmd.setErrors(errors);
        return rmd;
    }

    /**** THE OBJECT ****/

    public class RestMapData {

        private ArrayList<String> errors;
        private ArrayList<Latitude> lats;
        private ArrayList<Longitude> lons;
        private Page<ActivityLog> activityLog;
        private Page<Profile> userProfiles;
        private Profile profile;
        private Location location;
        private Collection<Bounty> bounties;

        public RestMapData(ArrayList<Latitude> lats, ArrayList<Longitude> lons, Profile profile,
                           Location location, Collection<Bounty> bounties) {
            this.lats = lats;
            this.lons = lons;
            this.location = location;
            this.bounties = bounties;
            this.profile = profile;
            this.errors = new ArrayList<>();
        }

        public Profile getProfile() { return profile; }

        public void setProfile(Profile profile) { this.profile = profile; }

        public Longitude getXpos() {
            return profile.getXpos();
        }

        public Latitude getYpos() {
            return profile.getYpos();
        }

        public ArrayList<Latitude> getLats() {
            return lats;
        }

        public void setLats(ArrayList<Latitude> lats) {
            this.lats = lats;
        }

        public ArrayList<Longitude> getLons() {
            return lons;
        }

        public void setLons(ArrayList<Longitude> lons) {
            this.lons = lons;
        }

        public void setActivityLog(Page<ActivityLog> activityLog) {
            this.activityLog = activityLog;
        }

        public Page<Profile> getUserProfiles() {
            return userProfiles;
        }

        public void setUserProfiles(Page<Profile> userProfiles) {
            this.userProfiles = userProfiles;
        }

        public Page<ActivityLog> getActivityLog() {
            return activityLog;
        }

        public HashMap<Stat, ProfileStat> getStatMap() {
            return profile.getStatMap();
        }

        public Collection<Bounty> getBounties() {
            return bounties;
        }

        public void setBounty(Collection<Bounty> bounties) {
            this.bounties = bounties;
        }

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public ArrayList<String> getErrors() {
            return errors;
        }

        public void setErrors(ArrayList<String>  errors) {
            this.errors = errors;
        }

        public void addError(String error) {
            errors.add(error);
        }
    }

}
