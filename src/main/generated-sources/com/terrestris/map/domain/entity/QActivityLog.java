package com.terrestris.map.domain.entity;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QActivityLog is a Querydsl query type for ActivityLog
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QActivityLog extends EntityPathBase<ActivityLog> {

    private static final long serialVersionUID = -2047141112L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QActivityLog activityLog = new QActivityLog("activityLog");

    public final EnumPath<com.terrestris.map.domain.object.Activity> aid = createEnum("aid", com.terrestris.map.domain.object.Activity.class);

    public final NumberPath<Long> alid = createNumber("alid", Long.class);

    public final DateTimePath<java.util.Date> atstamp = createDateTime("atstamp", java.util.Date.class);

    public final NumberPath<Integer> count = createNumber("count", Integer.class);

    public final QItem iid;

    public final QProfile otherPid;

    public final QProfile pid;

    public final QUser uid;

    public final QLongitude xpos;

    public final QLatitude ypos;

    public QActivityLog(String variable) {
        this(ActivityLog.class, forVariable(variable), INITS);
    }

    public QActivityLog(Path<? extends ActivityLog> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QActivityLog(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QActivityLog(PathMetadata<?> metadata, PathInits inits) {
        this(ActivityLog.class, metadata, inits);
    }

    public QActivityLog(Class<? extends ActivityLog> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.iid = inits.isInitialized("iid") ? new QItem(forProperty("iid")) : null;
        this.otherPid = inits.isInitialized("otherPid") ? new QProfile(forProperty("otherPid"), inits.get("otherPid")) : null;
        this.pid = inits.isInitialized("pid") ? new QProfile(forProperty("pid"), inits.get("pid")) : null;
        this.uid = inits.isInitialized("uid") ? new QUser(forProperty("uid")) : null;
        this.xpos = inits.isInitialized("xpos") ? new QLongitude(forProperty("xpos")) : null;
        this.ypos = inits.isInitialized("ypos") ? new QLatitude(forProperty("ypos")) : null;
    }

}

