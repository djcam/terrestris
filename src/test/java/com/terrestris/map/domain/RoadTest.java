package com.terrestris.map.domain;

import com.terrestris.map.domain.entity.*;

import com.terrestris.map.domain.repository.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.terrestris.map.domain.predicate.RoadPredicates.latitudeInGrid;


/**
 * Created by dcampbell2 on 3/2/2015.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplication.class)
//@WebIntegrationTest
public class RoadTest {

    @Autowired
    LatitudeRepository latRepo;
    @Autowired
    LongitudeRepository lonRepo;


    @Test
    public void allTest() {
        System.out.println("First test:");
        System.out.println(latRepo.findFirstByOrderByRidAsc());

        System.out.println("Last test:");
        System.out.println(latRepo.findFirstByOrderByRidDesc());

        for (Longitude lon : lonRepo.findAll()) {
            System.out.println(lon);
        }
        for (Latitude lat : latRepo.findAll()) {
            System.out.println(lat);
        }
    }



}
