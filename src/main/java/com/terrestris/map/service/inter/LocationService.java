package com.terrestris.map.service.inter;

import com.terrestris.map.domain.entity.Latitude;
import com.terrestris.map.domain.entity.Location;
import com.terrestris.map.domain.entity.Longitude;
import com.terrestris.map.domain.object.RpgClass;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * Created by dcampbell2 on 3/30/2015.
 */
public interface LocationService {
    Optional<Location> getLocationByLid(long lid);

    Optional<Location> getLocationByCoords(Longitude xpos, Latitude ypos);

    Optional<Location> getLocationByLname(String lname);

    Collection<Location> getAllLocations();

    ArrayList<Location> getAllLocationsByRpgcidAndLtype(RpgClass rpgcid, int ltype);
}
