package sg.com.cic.cicportal.web.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by minisha on 22/1/16.
 */
@Controller
@Slf4j
public class MainController {

    @RequestMapping("/")
    public ModelAndView login() throws IOException {
        log.info("Invoking login");
        return getView();
    }

    private ModelAndView getView() {
        return new ModelAndView("index");
    }
}
