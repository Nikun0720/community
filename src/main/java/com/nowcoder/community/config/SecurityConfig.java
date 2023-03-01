package com.nowcoder.community.config;

import com.nowcoder.community.util.CommunityConstant;
import com.nowcoder.community.util.CommunityUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter implements CommunityConstant {

    /**
     * 忽略对静态资源的访问
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

    /**
     * 认证那个就不做了，因为这个项目之前做过登录和退出认证，之后配置让Security绕过自带的登录和退出认证
     * Security自带的认证方法如下
     * protected void configure(AuthenticationManagerBuilder auth) throws Exception {}
     * 具体实现可以看那个SecurityDemo中的
     */

    /**
     * 授权
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
          授权
          这段代码的意思是，对于hasAnyAuthority内的三种权限，antMatchers内的这些请求时可以访问到
          anyRequest().permitAll()表示对于其他的请求，任何权限都可以访问（包括未登录）
          .and().csrf().disable()是用来关闭csrf认证的
          关闭原因是csrf对于同步的请求虽然能生成csrf，但是异步的就需要自己到html和js里处理（index.html和index.js中有演示），比较麻烦
         */
        http.authorizeRequests()
                .antMatchers(
                        "/user/setting",
                        "/user/upload",
                        "/discuss/add",
                        "/comment/add/**",
                        "/letter/**",
                        "/notice/**",
                        "/like",
                        "/follow",
                        "/unfollow"
                )
                .hasAnyAuthority(
                        AUTHORITY_USER,
                        AUTHORITY_ADMIN,
                        AUTHORITY_MODERATOR
                )
                .antMatchers(
                        "/discuss/top",
                        "/discuss/wonderful"
                )
                .hasAnyAuthority(
                        AUTHORITY_MODERATOR
                )
                .antMatchers(
                        "/discuss/delete",
                        "/data/**"
                )
                .hasAnyAuthority(
                        AUTHORITY_ADMIN
                )
                .anyRequest().permitAll()
                .and().csrf().disable();

        /*
          权限不够时的处理
          authenticationEntryPoint()表示没有登录时的处理
          accessDeniedHandler()表示权限不足时的处理
         */
        http.exceptionHandling()
                .authenticationEntryPoint(new AuthenticationEntryPoint() {
                    // 没有登录
                    @Override
                    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
                        // 获取这个请求是同步的还是异步的
                        // 同步的需要返回一个页面，异步的则需要返回一个json字符串
                        String xRequestedWith = request.getHeader("x-requested-with");

                        if("XMLHttpRequest".equals(xRequestedWith)) {
                            // 如果该字符串是"XMLHttpRequest"，那么这个请求就是异步的
                            response.setContentType("application/plain;charset=utf-8");

                            PrintWriter writer = response.getWriter();
                            writer.write(CommunityUtil.getJSONString(403, "您还没有登录！"));
                        }else {
                            // 如果该字符串不是"XMLHttpRequest"，那么这个请求就是同步的
                            response.sendRedirect(request.getContextPath() + "/login");
                        }
                    }
                })
                .accessDeniedHandler(new AccessDeniedHandler() {
                    // 登录了但权限不足
                    @Override
                    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
                        String xRequestedWith = request.getHeader("x-requested-with");

                        if("XMLHttpRequest".equals(xRequestedWith)) {
                            // 如果该字符串是"XMLHttpRequest"，那么这个请求就是异步的
                            response.setContentType("application/plain;charset=utf-8");

                            PrintWriter writer = response.getWriter();
                            writer.write(CommunityUtil.getJSONString(403, "您没有访问此功能的权限！"));
                        }else {
                            // 如果该字符串不是"XMLHttpRequest"，那么这个请求就是同步的
                            response.sendRedirect(request.getContextPath() + "/denied");
                        }
                    }
                });

        /*
          Security底层默认会拦截/logout请求，进行退出处理
          覆盖Security默认的逻辑才能执行自己的退出代码
          （底层代码专门对“/logout”这个请求拦截）
          （所以要么让Security去拦截一个别的没用的路径，就是下方的logoutUrl这种方式）
          （要么就自己退出的逻辑别用"/logout"这个请求（当然这个比较蠢））
         */
        http.logout().logoutUrl("/securitylogout");
    }
}
