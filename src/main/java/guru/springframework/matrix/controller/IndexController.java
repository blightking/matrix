package guru.springframework.matrix.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping({"/", "/index", "/main"})
    public String index() {
        return "index";
    }

    @RequestMapping({"/gauss"})
    public String methodGauss() {
        return "gauss";
    }

}
