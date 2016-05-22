package com.nexusy.oauth.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author lanhuidong
 * @since 2016-05-22
 */
@Controller
public class IndexController {

    @RequestMapping(value = {"/", "/index"})
    public ModelAndView index() {
        return new ModelAndView("/index");
    }
}
