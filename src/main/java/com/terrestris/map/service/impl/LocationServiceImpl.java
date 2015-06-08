package com.terrestris.map.service.impl;

import com.terrestris.map.domain.entity.Latitude;
import com.terrestris.map.domain.entity.Location;
import com.terrestris.map.domain.entity.Longitude;
import com.terrestris.map.domain.object.RpgClass;
import com.terrestris.map.domain.repository.LocationRepository;
import com.terrestris.map.service.inter.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * Created by dcampbell2 on 3/30/2015.
 */

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    @Autowired
    LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public Optional<Location> getLocationByLid(long lid) {
        return Optional.ofNullable(locationRepository.findOne(lid));
    }

    @Override
    public Optional<Location> getLocationByCoords(Longitude xpos, Latitude ypos) {
        return locationRepository.findOneByXposAndYpos(xpos, ypos);
    }

    @Override
    public Optional<Location> getLocationByLname(String lname) {
        return locationRepository.findOneByLname(lname);
    }

    @Override
    public Collection<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    @Override
    public ArrayList<Location> getAllLocationsByRpgcidAndLtype(RpgClass rpgcid, int ltype) {
        return locationRepository.findAllByRpgcidAndLtype(rpgcid, ltype);
    }
}
