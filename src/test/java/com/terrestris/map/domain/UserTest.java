package com.terrestris.map.domain;

import com.terrestris.map.domain.entity.*;

import com.terrestris.map.domain.repository.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * Created by dcampbell2 on 3/2/2015.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplication.class)
//@WebIntegrationTest
public class UserTest {

    @Autowired
    UserRepository userRepo;

    @Autowired
    ProfileRepository profileRepo;

    @Autowired
    LatitudeRepository latRepo;

    @Autowired
    LongitudeRepository lonRepo;

    @Test
    public void allTest() {
        System.out.println("First test:");

        for (User user : userRepo.findAll(new PageRequest(0, 10, new Sort("email")))) {
            System.out.println(user);
        }
    }

    /*
    @Test
    public void profileCreateTest() {
        RoadService roadService = new RoadServiceImpl(latRepo, lonRepo);

        for (User user : userRepo.findAll()) {
            int yLow = (int)roadService.getLatitudeRange().get(0).get().getRid();
            int yHigh = (int)roadService.getLatitudeRange().get(1).get().getRid();
            int xLow = (int)roadService.getLongitudeRange().get(0).get().getRid();
            int xHigh = (int)roadService.getLongitudeRange().get(1).get().getRid();

            Random r = new Random();
            Profile profile = new Profile();

            profile.setLevel(1);
            profile.setHandle(user.getFirst().substring(0, 1) + user.getLast());
            profile.setXpos(Long.parseLong(String.valueOf(r.nextInt(xHigh - xLow) + xLow)));
            profile.setYpos(Long.parseLong(String.valueOf(r.nextInt(yHigh - yLow) + yLow)));
            profile.setRpgcid(rpgClassRepo.findOne(Long.parseLong(String.valueOf(r.nextInt(4) + 1))));
            profile.setUid(user);

            profile.setMaxsp(100);
            profile.setMaxmp(100);
            profile.setMaxxp(100);
            profile.setSp(profile.getMaxsp());
            profile.setMp(profile.getMaxmp());
            profile.setXp(0);

            try {
                profileRepo.save(profile);
            } catch (Exception e) {
                System.out.println("error saving profile with handle " + profile.getHandle());
            }
        }
    }
    */
}
