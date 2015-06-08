package com.terrestris.map.domain.entity;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 705266136L;

    public static final QUser user = new QUser("user");

    public final StringPath email = createString("email");

    public final StringPath first = createString("first");

    public final EnumPath<com.terrestris.map.domain.object.Gender> gender = createEnum("gender", com.terrestris.map.domain.object.Gender.class);

    public final StringPath last = createString("last");

    public final StringPath passwordHash = createString("passwordHash");

    public final EnumPath<com.terrestris.map.domain.object.Role> role = createEnum("role", com.terrestris.map.domain.object.Role.class);

    public final EnumPath<com.terrestris.map.domain.object.UserStatus> status = createEnum("status", com.terrestris.map.domain.object.UserStatus.class);

    public final NumberPath<Long> uid = createNumber("uid", Long.class);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata<?> metadata) {
        super(User.class, metadata);
    }

}

