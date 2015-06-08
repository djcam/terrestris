package com.terrestris.map.domain.entity;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QInventory is a Querydsl query type for Inventory
 */
@Generated("com.mysema.query.codegen.SupertypeSerializer")
public class QInventory extends EntityPathBase<Inventory> {

    private static final long serialVersionUID = -1105575057L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInventory inventory = new QInventory("inventory");

    public final NumberPath<Integer> count = createNumber("count", Integer.class);

    public final QItem iid;

    public final NumberPath<Long> invid = createNumber("invid", Long.class);

    public QInventory(String variable) {
        this(Inventory.class, forVariable(variable), INITS);
    }

    public QInventory(Path<? extends Inventory> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QInventory(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QInventory(PathMetadata<?> metadata, PathInits inits) {
        this(Inventory.class, metadata, inits);
    }

    public QInventory(Class<? extends Inventory> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.iid = inits.isInitialized("iid") ? new QItem(forProperty("iid")) : null;
    }

}

