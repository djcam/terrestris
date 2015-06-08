package com.terrestris.map.domain.predicate;

import com.mysema.query.types.Predicate;
import com.terrestris.map.domain.entity.Latitude;
import com.terrestris.map.domain.entity.Longitude;
import com.terrestris.map.domain.entity.QLatitude;
import com.terrestris.map.domain.entity.QLongitude;

/**
 * Created by dcampbell2 on 3/3/2015.
 */
public class RoadPredicates {
    public static Predicate latitudeInGrid(final Latitude y) {
        QLatitude lat = QLatitude.latitude;
        return lat.rid.between(y.getRid() - 1, y.getRid() + 1);
    }

    public static Predicate longitudeInGrid(final Longitude x) {
        QLongitude lon = QLongitude.longitude;
        return lon.rid.between(x.getRid() - 1, x.getRid() + 1);
    }
}
