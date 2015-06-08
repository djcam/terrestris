package com.terrestris.map.domain.predicate;

/**
 * Created by dcampbell2 on 3/30/2015.
 */
import com.mysema.query.types.Predicate;
import com.terrestris.map.domain.entity.Latitude;
import com.terrestris.map.domain.entity.Longitude;
import com.terrestris.map.domain.entity.QProfile;

public class ProfilePredicates {

    public static Predicate profileOnSquare(final Longitude lon, final Latitude lat) {
        QProfile profile = QProfile.profile;
        return profile.xpos.rid.eq(lon.getRid()).and(profile.ypos.rid.eq(lat.getRid()));
    }
}
