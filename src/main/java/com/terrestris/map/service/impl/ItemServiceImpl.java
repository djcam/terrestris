package com.terrestris.map.service.impl;

import com.terrestris.map.domain.entity.Item;
import com.terrestris.map.domain.repository.ItemRepository;
import com.terrestris.map.service.inter.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by dcampbell2 on 4/6/2015.
 */

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public ArrayList<Item> getAll() {
        ArrayList<Item> list = new ArrayList<>();
        list.addAll(itemRepository.findAll());
        return list;
    }

    @Override
    public ArrayList<Item> getAllByDrop(int drop) {
        ArrayList<Item> list = new ArrayList<>();
        list.addAll(itemRepository.findAllByDropOrderByValueAsc(drop));
        return list;
    }

    @Override
    public Item getOneByIid(long iid) {
        return itemRepository.getOne(iid);
    }

    @Override
    public Item getLevelBoon() {
        return getOneByIid(LEVEL_BOON);
    }
}
