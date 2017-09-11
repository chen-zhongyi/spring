package spitter.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

/**
 * Created by 陈忠意 on 2017/8/16.
 */
@ControllerAdvice( basePackages = "spitter.web")
public class JsonpAdvice extends AbstractJsonpResponseBodyAdvice{

    public JsonpAdvice(){
        super("callback", "jsonp");
    }
}
