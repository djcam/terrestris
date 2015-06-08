package com.terrestris.map.domain.entity;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QProfile is a Querydsl query type for Profile
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QProfile extends EntityPathBase<Profile> {

    private static final long serialVersionUID = -558752644L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProfile profile = new QProfile("profile");

    public final StringPath handle = createString("handle");

    public final NumberPath<Integer> level = createNumber("level", Integer.class);

    public final NumberPath<Integer> markup = createNumber("markup", Integer.class);

    public final NumberPath<Integer> mvcost = createNumber("mvcost", Integer.class);

    public final NumberPath<Long> pid = createNumber("pid", Long.class);

    public final SetPath<ProfilePerk, QProfilePerk> profilePerks = this.<ProfilePerk, QProfilePerk>createSet("profilePerks", ProfilePerk.class, QProfilePerk.class, PathInits.DIRECT2);

    public final SetPath<ProfileStat, QProfileStat> profileStats = this.<ProfileStat, QProfileStat>createSet("profileStats", ProfileStat.class, QProfileStat.class, PathInits.DIRECT2);

    public final EnumPath<com.terrestris.map.domain.object.RpgClass> rpgcid = createEnum("rpgcid", com.terrestris.map.domain.object.RpgClass.class);

    public final NumberPath<Long> triste = createNumber("triste", Long.class);

    public final QUser uid;

    public final QLongitude xpos;

    public final QLatitude ypos;

    public QProfile(String variable) {
        this(Profile.class, forVariable(variable), INITS);
    }

    public QProfile(Path<? extends Profile> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QProfile(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QProfile(PathMetadata<?> metadata, PathInits inits) {
        this(Profile.class, metadata, inits);
    }

    public QProfile(Class<? extends Profile> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.uid = inits.isInitialized("uid") ? new QUser(forProperty("uid")) : null;
        this.xpos = inits.isInitialized("xpos") ? new QLongitude(forProperty("xpos")) : null;
        this.ypos = inits.isInitialized("ypos") ? new QLatitude(forProperty("ypos")) : null;
    }

}

