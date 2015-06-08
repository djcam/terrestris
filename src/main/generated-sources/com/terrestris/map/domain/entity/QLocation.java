package com.terrestris.map.domain.entity;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QLocation is a Querydsl query type for Location
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QLocation extends EntityPathBase<Location> {

    private static final long serialVersionUID = -1533122750L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLocation location = new QLocation("location");

    public final NumberPath<Long> lid = createNumber("lid", Long.class);

    public final NumberPath<Long> llife = createNumber("llife", Long.class);

    public final StringPath lname = createString("lname");

    public final DateTimePath<java.util.Date> lstamp = createDateTime("lstamp", java.util.Date.class);

    public final NumberPath<Integer> ltype = createNumber("ltype", Integer.class);

    public final NumberPath<Integer> markup = createNumber("markup", Integer.class);

    public final EnumPath<com.terrestris.map.domain.object.RpgClass> rpgcid = createEnum("rpgcid", com.terrestris.map.domain.object.RpgClass.class);

    public final QLongitude xpos;

    public final QLatitude ypos;

    public QLocation(String variable) {
        this(Location.class, forVariable(variable), INITS);
    }

    public QLocation(Path<? extends Location> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QLocation(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QLocation(PathMetadata<?> metadata, PathInits inits) {
        this(Location.class, metadata, inits);
    }

    public QLocation(Class<? extends Location> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.xpos = inits.isInitialized("xpos") ? new QLongitude(forProperty("xpos")) : null;
        this.ypos = inits.isInitialized("ypos") ? new QLatitude(forProperty("ypos")) : null;
    }

}

