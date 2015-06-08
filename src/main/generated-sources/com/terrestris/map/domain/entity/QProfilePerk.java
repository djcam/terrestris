package com.terrestris.map.domain.entity;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QProfilePerk is a Querydsl query type for ProfilePerk
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QProfilePerk extends EntityPathBase<ProfilePerk> {

    private static final long serialVersionUID = -952277622L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProfilePerk profilePerk = new QProfilePerk("profilePerk");

    public final NumberPath<Integer> active = createNumber("active", Integer.class);

    public final DateTimePath<java.util.Date> lastUse = createDateTime("lastUse", java.util.Date.class);

    public final EnumPath<com.terrestris.map.domain.object.Perk> perkid = createEnum("perkid", com.terrestris.map.domain.object.Perk.class);

    public final QProfile pid;

    public final NumberPath<Long> pkid = createNumber("pkid", Long.class);

    public final NumberPath<Integer> rate = createNumber("rate", Integer.class);

    public QProfilePerk(String variable) {
        this(ProfilePerk.class, forVariable(variable), INITS);
    }

    public QProfilePerk(Path<? extends ProfilePerk> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QProfilePerk(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QProfilePerk(PathMetadata<?> metadata, PathInits inits) {
        this(ProfilePerk.class, metadata, inits);
    }

    public QProfilePerk(Class<? extends ProfilePerk> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.pid = inits.isInitialized("pid") ? new QProfile(forProperty("pid"), inits.get("pid")) : null;
    }

}

