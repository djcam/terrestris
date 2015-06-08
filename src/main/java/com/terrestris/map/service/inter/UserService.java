package com.terrestris.map.service.inter;

import com.terrestris.map.domain.entity.User;
import com.terrestris.map.form.UserForm;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by dcampbell2 on 2/26/2015.
 */

public interface UserService {

    Optional<User> getUserByUid(long uid);

    Optional<User> getUserByEmail(String email);

    Collection<User> getAllUsers();

    Page<User> getAllUsersPageable(int start, int pageSize);

    User create(UserForm form);

}
