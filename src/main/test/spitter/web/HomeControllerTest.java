package spitter.web;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by 陈忠意 on 2017/7/17.
 */
public class HomeControllerTest {

    @Test
    public void testHomePage() throws Exception{
        HomeController controller = new HomeController();
        assertEquals("home", controller.home());
    }
}
