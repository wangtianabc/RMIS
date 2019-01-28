package com.cics.rmis.controller;

import com.cics.rmis.exception.MyException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/TestController")
public class TestController {

    @RequestMapping("/hello")
    public String index(@RequestParam("browser") String browser, HttpServletRequest request, HttpSession session){
        Map map = request.getParameterMap();
        Object sessionBrowser = session.getAttribute("browser");
        if (sessionBrowser == null) {
            System.out.println("不存在session，设置browser=" + browser);
            session.setAttribute("browser", browser);
        } else {
            System.out.println("存在session，browser=" + sessionBrowser.toString());
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                System.out.println(cookie.getName() + " : " + cookie.getValue());
            }
        }
        return "Hello Spring Boot World! 你好！ 世界！";
    }

    @RequestMapping("/json")
    public String json() throws MyException {
        throw new MyException("error");
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Map login(@RequestParam(value = "username", required = true) String username,
                     @RequestParam(value = "password", required = true) String password) {
        System.out.println(username);
        Map map = new HashMap<String,String>();
        return map;
    }
}
