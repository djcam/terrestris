package com.terrestris.map.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Arrays;
import java.util.List;

/**
 * Created by dcampbell2 on 3/12/2015.
 */

@Controller
public abstract class TerrestrisController {

    protected List<String> cssFiles;
    protected List<String> jsFiles;
    protected String subTitle;

    @ModelAttribute("subTitle")
    public String getSubTitle () {
        return subTitle;
    }

    @ModelAttribute("cssFiles")
    public List<String> populateCssFiles() { return cssFiles; }

    @ModelAttribute("jsFiles")
    public List<String> populateJsFiles() { return jsFiles; }
}
