package com.terrestris.map.domain.entity;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QBounty is a Querydsl query type for Bounty
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QBounty extends EntityPathBase<Bounty> {

    private static final long serialVersionUID = -1391247496L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBounty bounty = new QBounty("bounty");

    public final NumberPath<Long> bid = createNumber("bid", Long.class);

    public final StringPath bname = createString("bname");

    public final NumberPath<Integer> evasion = createNumber("evasion", Integer.class);

    public final QLocation lid;

    public final NumberPath<Integer> maxsp = createNumber("maxsp", Integer.class);

    public final NumberPath<Integer> mvcost = createNumber("mvcost", Integer.class);

    public final QProfile pid;

    public final NumberPath<Integer> sp = createNumber("sp", Integer.class);

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public final NumberPath<Integer> value = createNumber("value", Integer.class);

    public final NumberPath<Integer> xp = createNumber("xp", Integer.class);

    public final QLongitude xpos;

    public final QLatitude ypos;

    public QBounty(String variable) {
        this(Bounty.class, forVariable(variable), INITS);
    }

    public QBounty(Path<? extends Bounty> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QBounty(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QBounty(PathMetadata<?> metadata, PathInits inits) {
        this(Bounty.class, metadata, inits);
    }

    public QBounty(Class<? extends Bounty> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.lid = inits.isInitialized("lid") ? new QLocation(forProperty("lid"), inits.get("lid")) : null;
        this.pid = inits.isInitialized("pid") ? new QProfile(forProperty("pid"), inits.get("pid")) : null;
        this.xpos = inits.isInitialized("xpos") ? new QLongitude(forProperty("xpos")) : null;
        this.ypos = inits.isInitialized("ypos") ? new QLatitude(forProperty("ypos")) : null;
    }

}

