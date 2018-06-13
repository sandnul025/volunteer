package com.bankledger.interceptor;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.bankledger.constant.Constant;
import com.bankledger.entity.User;

public class LoginInterceptor implements HandlerInterceptor {

    private List<String> notNeedloginUrls = Arrays.asList(
    		"/",
    		"/error",
    		"/login",
    		"/register",
    		"/project/getListPublishing",// 所有发布的活动
    		"/join/getListPerson");// 某个活动的参加人员
    

    private final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {

        String url = request.getRequestURI().replace(request.getContextPath(),"");
        User user = (User)WebUtils.getSessionAttribute(request, Constant.login_session_key);
        logger.info("################ LoginInterceptor ################");
        logger.info("# user --->{}",user);
        logger.info("# url --->{}",url);
        logger.info("# ip --->{}\r\n",getRemoteHost(request));

        if( null == user){
            // 没有登录 判断该url 是否需要登录
            if(!notNeedloginUrls.contains(url)){
                returnResult(request, response);
            }else{
                return true;
            }
        }else{
        	if(url.equals("/login")) {
        		response.sendRedirect(request.getContextPath() + "/main");
        	}else {
        		return true;
        	}
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView mv) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception e) throws Exception {

    }

    protected String getRemoteHost(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
    }

    protected void returnResult(HttpServletRequest request,HttpServletResponse response) throws IOException {
        //ajax
        String header = request.getHeader("X-Requested-With");
        if("XMLHttpRequest".equalsIgnoreCase(header)){
            logger.info("当前请求为Ajax请求");
            String content = "{\"code\":\"3\",\"message\":\"请重新登录\"}";
            byte [] bodys = content.getBytes("UTF-8");
            OutputStream os = null;
            try {
                os = response.getOutputStream();
                os.write(bodys);
                response.setContentType("text/html;charset=UTF-8");
                response.setContentLength(bodys.length);
            } catch (Exception e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            } finally {
                if(null != os) {
                    os.flush();
                    os.close();
                    response.flushBuffer();
                }
            }
        }else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}
