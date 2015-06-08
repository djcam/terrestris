package com.terrestris.map.domain.entity;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QItem is a Querydsl query type for Item
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QItem extends EntityPathBase<Item> {

    private static final long serialVersionUID = 704909600L;

    public static final QItem item1 = new QItem("item1");

    public final NumberPath<Integer> drop = createNumber("drop", Integer.class);

    public final NumberPath<Long> iid = createNumber("iid", Long.class);

    public final StringPath item = createString("item");

    public final EnumPath<com.terrestris.map.domain.object.InventoryType> itype = createEnum("itype", com.terrestris.map.domain.object.InventoryType.class);

    public final NumberPath<Integer> maxcount = createNumber("maxcount", Integer.class);

    public final NumberPath<Integer> potency = createNumber("potency", Integer.class);

    public final EnumPath<com.terrestris.map.domain.object.Stat> stid = createEnum("stid", com.terrestris.map.domain.object.Stat.class);

    public final NumberPath<Integer> value = createNumber("value", Integer.class);

    public QItem(String variable) {
        super(Item.class, forVariable(variable));
    }

    public QItem(Path<? extends Item> path) {
        super(path.getType(), path.getMetadata());
    }

    public QItem(PathMetadata<?> metadata) {
        super(Item.class, metadata);
    }

}

