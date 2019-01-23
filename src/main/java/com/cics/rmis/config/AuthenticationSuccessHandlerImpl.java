package com.cics.rmis.config;

import com.cics.rmis.bean.RespBean;
import com.cics.rmis.model.TUser;
import com.cics.rmis.repository.TUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 登录成功信息
 */
@Service
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationSuccessHandlerImpl.class);

    @Autowired
    private TUserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        logger.info("AT onAuthenticationSuccess(...) function!");

        WebAuthenticationDetails details = (WebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        logger.info("login--IP:"+details.getRemoteAddress());

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication1 = context.getAuthentication();
        Object principal = authentication1.getPrincipal();
        Object principal1 = authentication.getPrincipal();

        String name = authentication.getName();
        logger.info("login--name:"+name+" principal:"+principal+" principal1:"+principal1);

        TUser user = userRepository.findByUsername(name);

        PrintWriter out = null;
        RespBean respBean = RespBean.ok("登录成功!", user);
        try {
            out = response.getWriter();
            ObjectMapper om = new ObjectMapper();
            out.write(om.writeValueAsString(respBean));
        } catch (IOException e){
            e.printStackTrace();
        }finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
