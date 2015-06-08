package com.terrestris.map.service.inter;

import com.terrestris.map.domain.entity.Item;

import java.util.ArrayList;

/**
 * Created by dcampbell2 on 4/6/2015.
 */
public interface ItemService {
    static final long LEVEL_BOON = 12;

    ArrayList<Item> getAll();
    ArrayList<Item> getAllByDrop(int drop);
    Item getOneByIid(long iid);
    Item getLevelBoon();
}
