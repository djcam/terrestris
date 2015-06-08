package com.terrestris.map.domain.entity;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QProfileInventory is a Querydsl query type for ProfileInventory
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QProfileInventory extends EntityPathBase<ProfileInventory> {

    private static final long serialVersionUID = -461215680L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProfileInventory profileInventory = new QProfileInventory("profileInventory");

    public final QInventory _super;

    //inherited
    public final NumberPath<Integer> count;

    // inherited
    public final QItem iid;

    //inherited
    public final NumberPath<Long> invid;

    public final QProfile pid;

    public QProfileInventory(String variable) {
        this(ProfileInventory.class, forVariable(variable), INITS);
    }

    public QProfileInventory(Path<? extends ProfileInventory> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QProfileInventory(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QProfileInventory(PathMetadata<?> metadata, PathInits inits) {
        this(ProfileInventory.class, metadata, inits);
    }

    public QProfileInventory(Class<? extends ProfileInventory> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QInventory(type, metadata, inits);
        this.count = _super.count;
        this.iid = _super.iid;
        this.invid = _super.invid;
        this.pid = inits.isInitialized("pid") ? new QProfile(forProperty("pid"), inits.get("pid")) : null;
    }

}

