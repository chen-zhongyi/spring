package spitter.web;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import spitter.JDBC;
import spitter.Spitter;
import spitter.SpitterRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 陈忠意 on 2017/7/17.
 */
@Controller
@RequestMapping({"/", "/homePage"})
@SessionAttributes("user")
public class HomeController {

    @ModelAttribute
    public void setUser(Model model){
        Map<String, String> userType = new HashMap<String, String>();
        userType.put("0", "admin");
        userType.put("1", "qxadmin");
        userType.put("2", "sample");
        userType.put("3", "yqadmin");
        model.addAttribute("userType", userType);
    }

    @Autowired
    private JDBC jdbc;

    @Autowired
    private SpitterRepository spitterRepository;

    @RequestMapping(method = RequestMethod.GET)
    public String home(){
        return "/WEB-INF/views/home.jsp";
    }

    @RequestMapping( value = "/home1", method = RequestMethod.GET)
    public String home1(@RequestParam("userName") String userName){
        System.out.println("buserName = " + userName);
        return "/WEB-INF/views/home.jsp";
    }

    @RequestMapping( value = "/home2/{id}", method = RequestMethod.GET)
    public String home2(@RequestParam("userName") String userName,
                        @RequestParam("password") String password,
                        @PathVariable("id") int id){
        System.out.println("{ id = " + id + ", userName = " + userName + ", password = " + password + " }");
        return "/WEB-INF/views/home.jsp";
    }

    @RequestMapping( value = "/register", method = RequestMethod.POST)
    public String register(@Valid Spitter spitter, Errors errors){
        if(errors.hasErrors()){
            System.out.println("Has erroes " + new Gson().toJson(errors));
            return "/WEB-INF/views/home.jsp";
        }
        //jdbc.addSpitter(spitter);
        spitterRepository.addSpitter(spitter);
        return "redirect:/";
    }

    @RequestMapping( value = "/update", method = RequestMethod.PUT)
    public String update(@Valid Spitter spitter, Errors errors){
        if(errors.hasErrors()){
            return "/WEB-INF/views/home.jsp";
        }
        spitterRepository.updateSpitter(spitter);
        return "/WEB-INF/views/home.jsp";
    }

    @RequestMapping( value = "/delete/{id}", method = RequestMethod.DELETE )
    public String delete(@PathVariable("id") Long id){
        spitterRepository.deleteSpitter(id);
        return "/WEB-INF/views/home.jsp";
    }

    @RequestMapping( value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody Spitter findOne(@PathVariable Long id){
        return spitterRepository.findOne(id);
    }

    @RequestMapping( value = "/json", method = RequestMethod.POST )
    public @ResponseBody Map<String, String> testRequestBody(@RequestBody Map<String, String> map, @ModelAttribute("userType") Map<String, String> model){
        System.out.println("modelMap -- " + model);
        return map;
    }

    @RequestMapping( value = "/header")
    public void getHttpHeader(HttpServletResponse response, @RequestHeader Map<String, String> header, @CookieValue("JSESSIONID") String cookie)throws IOException{
        //System.out.println(header);
        System.out.println("cookie -- " + cookie);
        for(String key : header.keySet()){
            System.out.println(key + " -- " + header.get(key));
        }
        response.setHeader("content-type", "application/json;charset=UTF-8");

        Writer writer = response.getWriter();
        String json = new Gson().toJson(header);
        writer.write(json);
        writer.flush();
        writer.close();
    }

    @RequestMapping( value = "/image", method = RequestMethod.POST)
    public String getFile(HttpServletRequest request, @RequestParam MultipartFile image)throws IOException{
        if(! image.isEmpty()){
            //byte[] b = image.getBytes();
            InputStream in = image.getInputStream();
            String path = request.getSession().getServletContext().getRealPath("/")  + "images"
                    + File.separator + image.getOriginalFilename();
            System.out.println("path : " + path);
            File file = new File(path);
            file.getParentFile().mkdirs();
            FileOutputStream out = new FileOutputStream(file);
            byte[] b = new byte[1024];
            int length = 0;
            while((length = in.read(b)) > 0){
                out.write(b, 0, length);
            }
            in.close();
            out.close();
        }
        return "/WEB-INF/views/home.jsp";
    }
}
