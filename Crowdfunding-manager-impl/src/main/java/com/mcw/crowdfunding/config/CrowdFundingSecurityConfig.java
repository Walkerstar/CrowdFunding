package com.mcw.crowdfunding.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author mcw 2019\12\9 0009-14:37
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CrowdFundingSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    /**
     * 1、自定义请求授权访问规则
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);
        http.authorizeRequests().antMatchers("/static/**","/welcome.jsp","/tologin").permitAll()
        .anyRequest().authenticated();//剩下的都需要认证

        // /login==POST  用户登陆请求发给Security
        http.formLogin().loginPage("/tologin")
                .usernameParameter("loginacct")
                .passwordParameter("userpswd")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/main");

        http.csrf().disable();

        //登出后，会跳转到登录页面，再次点击登录才会跳转到主页面
        http.logout();

        http.exceptionHandling().accessDeniedHandler(new AccessDeniedHandler() {
            @Override
            public void handle(HttpServletRequest Request, HttpServletResponse Response, AccessDeniedException e) throws IOException, ServletException {
                String type = Request.getHeader("X-Requested-With");
                if("XMLHttpRequest".equals(type)){
                    Response.getWriter().print("403");//403 拒绝访问，权限不够
                }else {
                    Request.getRequestDispatcher("/WEB-INF/jsp/error/error403.jsp").forward(Request,Response);
                }
            }
        });

        //开启记住我的功能
        http.rememberMe();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //super.configure(auth);
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
}
