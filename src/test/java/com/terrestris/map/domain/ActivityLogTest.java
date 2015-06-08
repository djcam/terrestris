package com.terrestris.map.domain;

import com.terrestris.map.domain.entity.ActivityLog;
import com.terrestris.map.domain.entity.Profile;
import com.terrestris.map.domain.repository.ActivityLogRepository;
import com.terrestris.map.domain.repository.ProfileRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.Optional;


/**
 * Created by dcampbell2 on 3/2/2015.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplication.class)
//@WebIntegrationTest
public class ActivityLogTest {

    @Autowired
    ActivityLogRepository alogRepo;

    @Autowired
    ProfileRepository profRepo;

    @Test
    public void allTest() {
        System.out.println("/*****************/");
        System.out.println("Activity Log Test:");

        System.out.println("All by handle:");
        Optional<Profile> pid = profRepo.findOneByHandle("Crashlight");
        Profile profile = pid.get();

        for (ActivityLog alog : alogRepo.findAllByPid(profile)) {
            Date d = new Date();
            long diff = d.getTime() - alog.getAtstamp().getTime();
            //System.out.println(alog);
            //System.out.println("TIME DIFF: " + diff);
        }

        System.out.println("Last Move:");
        Optional<ActivityLog> lastMove = alogRepo.findFirstByOrderByAtstampDesc();
        System.out.println(lastMove.get());

        System.out.println("Pageable by handle (1-10):");
        for (ActivityLog alog : alogRepo.findByPid(profile, new PageRequest(0, 10, Sort.Direction.DESC, "atstamp"))) {
            System.out.println(alog);
        }

        System.out.println("Pageable by handle (5-10):");
        for (ActivityLog alog : alogRepo.findByPid(profile, new PageRequest(1, 5, Sort.Direction.DESC, "atstamp"))) {
            System.out.println(alog);
        }
    }

}
