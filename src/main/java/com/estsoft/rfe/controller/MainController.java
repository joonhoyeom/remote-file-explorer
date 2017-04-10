package com.estsoft.rfe.controller;

import com.estsoft.rfe.annotation.Auth;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by joonho on 2017-04-05.
 */
@Controller
public class MainController {



    @RequestMapping("")
    public String index() {
        return "login";
    }

    @Auth
    @RequestMapping("main")
    public String main(Model model) {
        return "main";
    }
}
