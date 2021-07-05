package com.senla.project.socialnetwork.controller;

import com.senla.project.socialnetwork.entity.Blocklist;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/aot")
public class AuthController {

    @GetMapping("/login")
    //@PreAuthorize("hasAnyAuthority('communities:permission','standard:permission')")
    public String getLoginPage(){
        return "login";
    }

    @GetMapping("/success")
    public String getSuccessPage(){
        return "success";
    }

}
