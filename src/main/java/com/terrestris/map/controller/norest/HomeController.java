package com.terrestris.map.controller.norest;

import com.terrestris.map.config.Layout;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by dcampbell2 on 2/26/2015.
 */

@Controller
@Layout(value = "layouts/base")
public class HomeController {

    @RequestMapping("/home")
    public String getHomePage() {
        return "views/home";
    }
}
