package org.example.MatchYou.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/start")
    public String start() {
        return "start";
    }

    @GetMapping("/image")
    public String image() {
        return "image";
    }

    @GetMapping("/personal-color")
    public String personalColor() {
        return "personal_color";
    }

}
