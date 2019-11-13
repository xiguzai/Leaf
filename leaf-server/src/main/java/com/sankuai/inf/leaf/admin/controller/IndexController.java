package com.sankuai.inf.leaf.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping("/admin")
    public String index() {
        return "/admin/index.html";
    }
}
