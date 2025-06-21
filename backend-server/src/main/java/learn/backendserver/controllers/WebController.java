package learn.backendserver.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WebController {
    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/assets/{file}")
    public String assets(@PathVariable String file) {
        return String.format("assets/%s",file);
    }
}
