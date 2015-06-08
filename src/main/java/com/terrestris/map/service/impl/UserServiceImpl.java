package com.terrestris.map.service.impl;

import com.terrestris.map.domain.entity.User;
import com.terrestris.map.domain.repository.UserRepository;
import com.terrestris.map.form.UserForm;
import com.terrestris.map.service.inter.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by dcampbell2 on 2/26/2015.
 */

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> getUserByUid(long uid) {
        return Optional.ofNullable(userRepository.findOne(uid));
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findOneByEmail(email);
    }

    @Override
    public Collection<User> getAllUsers() {
        return userRepository.findAll(new Sort("email"));
    }

    @Override
    public Page<User> getAllUsersPageable(int start, int pageSize) {
        return userRepository.findAll(new PageRequest(start, pageSize, new Sort("email")));
    }

    @Override
    public User create(UserForm form) {
        User user = new User();
        user.setEmail(form.getEmail());
        user.setPasswordHash(new BCryptPasswordEncoder().encode(form.getPassword()));
        user.setRole(form.getRole());

        return userRepository.save(user);
    }
}
