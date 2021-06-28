package org.hiden.crowd.handler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PortalHandler {

    @RequestMapping("/")
    public String showPortal() {
        return "portal";
    }

}
