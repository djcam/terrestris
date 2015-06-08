package com.terrestris.map.controller.norest;


import com.terrestris.map.config.Layout;
import com.terrestris.map.service.inter.UserService;
import com.terrestris.map.form.UserForm;
import com.terrestris.map.form.UserFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.NoSuchElementException;

/**
 * Created by dcampbell2 on 2/26/2015.
 */

@Controller
@Layout(value = "layouts/base")
public class UserController {

    private final UserService userService;
    private final UserFormValidator userFormValidator;

    @Autowired
    public UserController(UserService userService, UserFormValidator userFormValidator) {
        this.userService = userService;
        this.userFormValidator = userFormValidator;
    }

    @InitBinder("form")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(userFormValidator);
    }

    @PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #uid)")
    @RequestMapping("/user/{uid}")
    public ModelAndView getUserPage(@PathVariable Long uid) {
        return new ModelAndView("views/user", "user", userService.getUserByUid(uid)
                .orElseThrow(() -> new NoSuchElementException(String.format("User=%s not found, uid"))));
    }

    @RequestMapping(value = "/user/create", method = RequestMethod.GET)
    @Layout(value = "layouts/auth")
    public ModelAndView getUserCreatePage() {
        return new ModelAndView("views/user_create", "form", new UserForm());
    }

    @RequestMapping(value = "/user/create", method = RequestMethod.POST)
    @Layout(value = "layouts/auth")
    public String handleUserForm(@Valid @ModelAttribute("form") UserForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "views/user_create";
        }
        try {
            userService.create(form);
        } catch (DataIntegrityViolationException e) {
            bindingResult.reject("email.exists", "Email already exists");
            return "views/user_create";
        }
        return "redirect:/users";
    }
}
