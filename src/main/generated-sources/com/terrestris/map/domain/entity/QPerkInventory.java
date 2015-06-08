package com.terrestris.map.domain.entity;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QPerkInventory is a Querydsl query type for PerkInventory
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QPerkInventory extends EntityPathBase<PerkInventory> {

    private static final long serialVersionUID = -60417375L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPerkInventory perkInventory = new QPerkInventory("perkInventory");

    public final QInventory _super;

    //inherited
    public final NumberPath<Integer> count;

    // inherited
    public final QItem iid;

    //inherited
    public final NumberPath<Long> invid;

    public final EnumPath<com.terrestris.map.domain.object.Perk> perkid = createEnum("perkid", com.terrestris.map.domain.object.Perk.class);

    public QPerkInventory(String variable) {
        this(PerkInventory.class, forVariable(variable), INITS);
    }

    public QPerkInventory(Path<? extends PerkInventory> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QPerkInventory(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QPerkInventory(PathMetadata<?> metadata, PathInits inits) {
        this(PerkInventory.class, metadata, inits);
    }

    public QPerkInventory(Class<? extends PerkInventory> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QInventory(type, metadata, inits);
        this.count = _super.count;
        this.iid = _super.iid;
        this.invid = _super.invid;
    }

}

