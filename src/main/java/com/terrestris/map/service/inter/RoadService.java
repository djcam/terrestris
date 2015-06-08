package com.terrestris.map.service.inter;

import com.terrestris.map.domain.entity.Latitude;
import com.terrestris.map.domain.entity.Longitude;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Created by dcampbell2 on 3/2/2015.
 */
public interface RoadService {

    Optional<Latitude> getLatitudeByRid(long rid);
    Optional<Latitude> getLatitudeByRname(String rname);
    Optional<Longitude> getLongitudeByRid(long rid);
    Optional<Longitude> getLongitudeByRname(String rname);

    Latitude getMinLatitude();
    Latitude getMaxLatitude();
    Longitude getMinLongitude();
    Longitude getMaxLongitude();

    Longitude getRandomLongitude();
    Latitude getRandomLatitude();
    Longitude getNextLongitude(Longitude curLon, boolean dir);
    Latitude getNextLatitude(Latitude curLat, boolean dir);

    ArrayList<Latitude> getLatitudeRange();
    ArrayList<Longitude> getLongitudeRange();

    ArrayList<Longitude> getLongitudesGrid(Longitude xpos);
    ArrayList<Latitude> getLatitudesGrid(Latitude ypos);

    Collection<Latitude> getAllLatitudes();
    Collection<Longitude> getAllLongitudes();
}
