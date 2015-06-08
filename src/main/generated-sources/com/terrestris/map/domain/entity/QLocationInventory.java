package com.terrestris.map.domain.entity;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QLocationInventory is a Querydsl query type for LocationInventory
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QLocationInventory extends EntityPathBase<LocationInventory> {

    private static final long serialVersionUID = 865524794L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLocationInventory locationInventory = new QLocationInventory("locationInventory");

    public final QInventory _super;

    //inherited
    public final NumberPath<Integer> count;

    // inherited
    public final QItem iid;

    //inherited
    public final NumberPath<Long> invid;

    public final QLocation lid;

    public QLocationInventory(String variable) {
        this(LocationInventory.class, forVariable(variable), INITS);
    }

    public QLocationInventory(Path<? extends LocationInventory> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QLocationInventory(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QLocationInventory(PathMetadata<?> metadata, PathInits inits) {
        this(LocationInventory.class, metadata, inits);
    }

    public QLocationInventory(Class<? extends LocationInventory> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QInventory(type, metadata, inits);
        this.count = _super.count;
        this.iid = _super.iid;
        this.invid = _super.invid;
        this.lid = inits.isInitialized("lid") ? new QLocation(forProperty("lid"), inits.get("lid")) : null;
    }

}

