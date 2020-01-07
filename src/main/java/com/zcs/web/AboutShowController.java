package com.zcs.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//关于我
@Controller
public class AboutShowController {


    /*----------------------------------关于我！-----------------------------------------*/
    @GetMapping("/about")
    public String about() {
        return "about";
    }
}
