package com.liuyh.hlsvod;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {
    @RequestMapping("/xgp")
    public String xgp(){
        return "xgp.html";
    }
}
