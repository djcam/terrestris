package com.terrestris.map.service.impl;

import com.mysema.query.types.OrderSpecifier;
import com.terrestris.map.domain.entity.*;
import com.terrestris.map.domain.repository.LatitudeRepository;
import com.terrestris.map.domain.repository.LongitudeRepository;
import com.terrestris.map.service.inter.RoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.terrestris.map.domain.predicate.RoadPredicates.longitudeInGrid;
import static com.terrestris.map.domain.predicate.RoadPredicates.latitudeInGrid;

/**
 * Created by dcampbell2 on 3/2/2015.
 */

@Service
public class RoadServiceImpl implements RoadService {

    private final LatitudeRepository latitudeRepository;
    private final LongitudeRepository longitudeRepository;
    private static int latMin;
    private static int latMax;
    private static int lonMin;
    private static int lonMax;

    @Autowired
    public RoadServiceImpl(LatitudeRepository latitudeRepository, LongitudeRepository longitudeRepository) {
        this.latitudeRepository = latitudeRepository;
        this.longitudeRepository = longitudeRepository;

        this.latMin = (int)getLatitudeRange().get(0).getRid();
        this.latMax = (int)getLatitudeRange().get(1).getRid();
        this.lonMin = (int)getLongitudeRange().get(0).getRid();
        this.lonMax = (int)getLongitudeRange().get(1).getRid();
    }

    @Override
    public Optional<Latitude> getLatitudeByRid(long rid) {
        return Optional.ofNullable(latitudeRepository.findOne(rid));
    }

    @Override
    public Optional<Latitude> getLatitudeByRname(String rname) {
        return latitudeRepository.findOneByRname(rname);
    }

    @Override
    public Optional<Longitude> getLongitudeByRid(long rid) {
        return Optional.ofNullable(longitudeRepository.findOne(rid));
    }

    @Override
    public Collection<Longitude> getAllLongitudes() {
        return longitudeRepository.findAll();
    }

    @Override
    public Collection<Latitude> getAllLatitudes() {
        return latitudeRepository.findAll();
    }

    @Override
    public Optional<Longitude> getLongitudeByRname(String rname) {
        return longitudeRepository.findOneByRname(rname);
    }

    @Override
    public Latitude getMinLatitude() {
        return latitudeRepository.findFirstByOrderByRidAsc();
    }

    @Override
    public Latitude getMaxLatitude() {
        return latitudeRepository.findFirstByOrderByRidDesc();
    }

    @Override
    public Longitude getMinLongitude() {
        return longitudeRepository.findFirstByOrderByRidAsc();
    }

    @Override
    public Longitude getMaxLongitude() {
        return longitudeRepository.findFirstByOrderByRidDesc();
    }

    @Override
    public Longitude getRandomLongitude() {
        Random r = new Random();
        long newLonRid = (long)r.nextInt(lonMax - lonMin) + lonMin;
        return getLongitudeByRid(newLonRid).get();
    }

    @Override
    public Latitude getRandomLatitude() {
        Random r = new Random();
        long newLatRid = (long)r.nextInt(latMax - latMin) + latMin;
        return getLatitudeByRid(newLatRid).get();
    }

    @Override
    public Longitude getNextLongitude(Longitude curLon, boolean dir) {
        long curRid = curLon.getRid();
        if (dir) {
            if (curRid + 1 <= lonMax) return longitudeRepository.findOne(curRid + 1);
            else return getMinLongitude();
        } else {
            if (curRid - 1 >= lonMin) return longitudeRepository.findOne(curRid - 1);
            else return getMaxLongitude();
        }
    }

    @Override
    public Latitude getNextLatitude(Latitude curLat, boolean dir) {
        long curRid = curLat.getRid();
        if (dir) {
            if (curRid + 1 <= latMax) return latitudeRepository.findOne(curRid + 1);
            else return getMinLatitude();
        } else {
            if (curRid - 1 >= latMin) return latitudeRepository.findOne(curRid - 1);
            else return  getMaxLatitude();
        }
    }

    @Override
    public ArrayList<Latitude> getLatitudeRange() {
        ArrayList<Latitude> range = new ArrayList<Latitude>();
        range.add(latitudeRepository.findFirstByOrderByRidAsc());
        range.add(latitudeRepository.findFirstByOrderByRidDesc());
        return range;
    }

    @Override
    public ArrayList<Longitude> getLongitudeRange() {
        ArrayList<Longitude> range = new ArrayList<Longitude>();
        range.add(longitudeRepository.findFirstByOrderByRidAsc());
        range.add(longitudeRepository.findFirstByOrderByRidDesc());
        return range;
    }

    @Override
    public ArrayList<Longitude> getLongitudesGrid(Longitude xpos) {
        Iterable<Longitude> lonCol = longitudeRepository.findAll(longitudeInGrid(xpos), orderLongByRidAsc());
        return constructLonList(lonCol);
    }

    @Override
    public ArrayList<Latitude> getLatitudesGrid(Latitude ypos) {
        ArrayList<Latitude> latCol = (ArrayList<Latitude>) latitudeRepository.findAll(latitudeInGrid(ypos));
        return constructLatList(latCol);
    }

    private OrderSpecifier<Long> orderLongByRidAsc() {
        return QLongitude.longitude.rid.asc();
    }

    private OrderSpecifier<Long> orderLatByRidAsc() {
        return QLatitude.latitude.rid.asc();
    }

    private ArrayList<Latitude> constructLatList(Iterable<Latitude> roads) {
        ArrayList<Latitude> list = new ArrayList<Latitude>();
        for (Latitude lat : roads) {
            list.add(lat);
        }
        if (list.size() < 3) {
            if (list.get(0).getRid() == latMin) {
                list.add(0, getMaxLatitude());
            } else {
                list.add(getMinLatitude());
            }
        }
        return list;
    }

    private ArrayList<Longitude> constructLonList(Iterable<Longitude> roads) {
        ArrayList<Longitude> list = new ArrayList<Longitude>();
        for (Longitude lon : roads) {
            list.add(lon);
        }
        if (list.size() < 3) {
            if (list.get(0).getRid() == lonMin) {
                list.add(0, getMaxLongitude());
            } else {
                list.add(getMinLongitude());
            }
        }
        return list;
    }
}
