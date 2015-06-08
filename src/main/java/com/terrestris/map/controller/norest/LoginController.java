package com.terrestris.map.controller.norest;

import com.terrestris.map.config.Layout;
import com.terrestris.map.controller.TerrestrisController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

/**
 * Created by dcampbell2 on 2/26/2015.
 */

@Controller
@Layout(value = "layouts/auth")
public class LoginController extends TerrestrisController {

    @RequestMapping(value="/", method = RequestMethod.GET)
    public ModelAndView getLoginPage(@RequestParam Optional<String> error) {
        return new ModelAndView("views/auth", "error", error);
    }

}
