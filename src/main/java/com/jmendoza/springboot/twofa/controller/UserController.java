package com.jmendoza.springboot.twofa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class UserController {
    @RequestMapping(value = "user", method = RequestMethod.GET)
    public ModelAndView user() {
        return new ModelAndView("user");
    }

    @RequestMapping(value = "qrcode", method = RequestMethod.GET)
    public ModelAndView qrcode() {
        return new ModelAndView("qrcode");
    }

    @RequestMapping(value = "qrcodevalidate", method = RequestMethod.GET)
    public ModelAndView qrcodevalidate() {
        return new ModelAndView("qrcodevalidate");
    }
}
