package com.terrestris.map.domain.repository;

import com.terrestris.map.domain.entity.Profile;
import com.terrestris.map.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import javax.swing.text.html.Option;
import java.util.Collection;
import java.util.Optional;

/**
 * Created by dcampbell2 on 3/4/2015.
 */
public interface ProfileRepository extends JpaRepository<Profile, Long>, QueryDslPredicateExecutor<Profile> {
    Collection<Profile> findAllByUid(User uid);
    Optional<Profile> findOneByHandle(String handle);
}
