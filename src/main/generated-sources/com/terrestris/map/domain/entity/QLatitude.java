package com.terrestris.map.domain.entity;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QLatitude is a Querydsl query type for Latitude
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QLatitude extends EntityPathBase<Latitude> {

    private static final long serialVersionUID = -579177479L;

    public static final QLatitude latitude = new QLatitude("latitude");

    public final QRoad _super = new QRoad(this);

    //inherited
    public final NumberPath<Long> rid = _super.rid;

    //inherited
    public final StringPath rname = _super.rname;

    //inherited
    public final StringPath suffix = _super.suffix;

    public QLatitude(String variable) {
        super(Latitude.class, forVariable(variable));
    }

    public QLatitude(Path<? extends Latitude> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLatitude(PathMetadata<?> metadata) {
        super(Latitude.class, metadata);
    }

}

