package com.terrestris.map.domain.entity;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QLongitude is a Querydsl query type for Longitude
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QLongitude extends EntityPathBase<Longitude> {

    private static final long serialVersionUID = 1052390338L;

    public static final QLongitude longitude = new QLongitude("longitude");

    public final QRoad _super = new QRoad(this);

    //inherited
    public final NumberPath<Long> rid = _super.rid;

    //inherited
    public final StringPath rname = _super.rname;

    //inherited
    public final StringPath suffix = _super.suffix;

    public QLongitude(String variable) {
        super(Longitude.class, forVariable(variable));
    }

    public QLongitude(Path<? extends Longitude> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLongitude(PathMetadata<?> metadata) {
        super(Longitude.class, metadata);
    }

}

