package com.terrestris.map.domain.entity;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QRoad is a Querydsl query type for Road
 */
@Generated("com.mysema.query.codegen.SupertypeSerializer")
public class QRoad extends EntityPathBase<Road> {

    private static final long serialVersionUID = 705172781L;

    public static final QRoad road = new QRoad("road");

    public final NumberPath<Long> rid = createNumber("rid", Long.class);

    public final StringPath rname = createString("rname");

    public final StringPath suffix = createString("suffix");

    public QRoad(String variable) {
        super(Road.class, forVariable(variable));
    }

    public QRoad(Path<? extends Road> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRoad(PathMetadata<?> metadata) {
        super(Road.class, metadata);
    }

}

