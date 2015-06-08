package com.terrestris.map.domain.repository;

import com.terrestris.map.domain.entity.Latitude;
import com.terrestris.map.domain.entity.Location;
import com.terrestris.map.domain.entity.Longitude;
import com.terrestris.map.domain.object.RpgClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by dcampbell2 on 3/30/2015.
 */

public interface LocationRepository extends JpaRepository<Location, Long> {
    Optional<Location> findOneByXposAndYpos(Longitude xpos, Latitude ypos);
    ArrayList<Location> findAllByRpgcidAndLtype(RpgClass rpgcid, int ltype);
    Optional<Location> findOneByLname(String lname);
}