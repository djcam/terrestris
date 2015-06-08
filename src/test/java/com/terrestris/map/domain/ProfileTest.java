package com.terrestris.map.domain;

import com.terrestris.map.domain.entity.*;
import com.terrestris.map.domain.object.Stat;
import com.terrestris.map.domain.repository.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import static com.terrestris.map.domain.predicate.ProfilePredicates.profileOnSquare;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by dcampbell2 on 3/24/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplication.class)
//@WebIntegrationTest
public class ProfileTest {

    @Autowired
    ProfileRepository profileRepo;
    @Autowired
    ProfileStatRepository profileStatRepo;
    @Autowired
    UserRepository userRepo;
    @Autowired
    BountyRepository bountyRepo;
    @Autowired
    LongitudeRepository lonRepo;
    @Autowired
    LatitudeRepository latRepo;
    @Autowired
    ProfileStatRepository pstatRepo;

    @Test
    public void profileTest() {
        System.out.println("Profile test:");
        Profile bprof = profileRepo.findOne((long)1);
        //assertEquals(bprof.getProfilePerks().size(), 3);
    }

}
