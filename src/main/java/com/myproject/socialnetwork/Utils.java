package com.myproject.socialnetwork;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class Utils {

    public static String getLogin() {
        return ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }

//    public static String getId(SecurityContext context) {
//        return ((UserDetails) context.getAuthentication().getPrincipal()).getUsername();
//    }

}
