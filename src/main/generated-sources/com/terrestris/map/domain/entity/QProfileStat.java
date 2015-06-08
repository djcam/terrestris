package com.terrestris.map.domain.entity;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QProfileStat is a Querydsl query type for ProfileStat
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QProfileStat extends EntityPathBase<ProfileStat> {

    private static final long serialVersionUID = -952174352L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProfileStat profileStat = new QProfileStat("profileStat");

    public final NumberPath<Integer> max = createNumber("max", Integer.class);

    public final QProfile pid;

    public final NumberPath<Long> pstid = createNumber("pstid", Long.class);

    public final NumberPath<Integer> rate = createNumber("rate", Integer.class);

    public final DateTimePath<java.util.Date> refill = createDateTime("refill", java.util.Date.class);

    public final EnumPath<com.terrestris.map.domain.object.Stat> stid = createEnum("stid", com.terrestris.map.domain.object.Stat.class);

    public final NumberPath<Integer> total = createNumber("total", Integer.class);

    public final NumberPath<Integer> value = createNumber("value", Integer.class);

    public QProfileStat(String variable) {
        this(ProfileStat.class, forVariable(variable), INITS);
    }

    public QProfileStat(Path<? extends ProfileStat> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QProfileStat(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QProfileStat(PathMetadata<?> metadata, PathInits inits) {
        this(ProfileStat.class, metadata, inits);
    }

    public QProfileStat(Class<? extends ProfileStat> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.pid = inits.isInitialized("pid") ? new QProfile(forProperty("pid"), inits.get("pid")) : null;
    }

}

