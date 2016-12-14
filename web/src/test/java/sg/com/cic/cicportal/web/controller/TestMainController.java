package sg.com.cic.cicportal.web.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by minisha on 22/1/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/dispatcher-servlet.xml" })
public class TestMainController {

    @Autowired
    private MainController mainController;

    @Test
    public void testLogin() throws IOException {
        ModelAndView result = mainController.login();
        assertThat(result.getViewName()).isEqualTo("index");
    }

}
