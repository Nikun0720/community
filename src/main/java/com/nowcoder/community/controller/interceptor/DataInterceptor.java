package com.nowcoder.community.controller.interceptor;

import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.DataService;
import com.nowcoder.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用拦截器来计入UV和DAU
 * 每次请求都应该被统计，所以用拦截器
 */
@Component
public class DataInterceptor implements HandlerInterceptor {

    @Autowired
    private DataService dataService;

    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 计入UV（独立访客）
        String ip = request.getRemoteHost();    // 获取ip
        dataService.recordUV(ip);

        //计入DAU（日活跃用户）
        User user = hostHolder.getUser();
        if(user != null) {
            dataService.recordDAU(user.getId());
        }

        return true;
    }
}
