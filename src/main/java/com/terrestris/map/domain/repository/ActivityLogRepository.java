package com.terrestris.map.domain.repository;

import com.terrestris.map.domain.entity.ActivityLog;
import com.terrestris.map.domain.entity.Profile;
import com.terrestris.map.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by dcampbell2 on 3/24/2015.
 */
public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long>, QueryDslPredicateExecutor<ActivityLog> {
    Collection<ActivityLog> findAllByPid(Profile pid);
    Collection<ActivityLog> findAllByUid(User uid);
    Page<ActivityLog> findByPid(Profile pid, Pageable pageRequest);
    Page<ActivityLog> findByUid(User uid, Pageable pageRequest);
    Optional<ActivityLog> findFirstByOrderByAtstampDesc();
}
