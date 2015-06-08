package com.terrestris.map.controller.norest;

import com.terrestris.map.config.Layout;
import com.terrestris.map.service.inter.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by dcampbell2 on 2/26/2015.
 */

@Controller
@Layout(value = "layouts/report")
public class UsersController {

    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/users")
    public ModelAndView getUsersPage() {
        return new ModelAndView("views/users", "users", userService.getAllUsersPageable(0, 20));
    }

}
