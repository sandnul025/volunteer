package com.bankledger.config;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * @AUTHOR: sandnul025
 * @MOTTO: Rainbow comes after a storm.
 * @DATE: 2018年3月27日 下午2:43:19
 * <p>
 * 可以利用切面 和 继承 springboot  默认的 errorController来处理异常
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private static final String ERROR_MESSAGE = "System error,please contact admin!";

    @ExceptionHandler(value = Throwable.class)
    public ModelAndView ExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
        logger.error("################# Exception ##################");
        logger.error("### RequestUrI: {} ", request.getRequestURI().replace(request.getContextPath(), ""));
        logger.error("### RequestParam: {} ", getAllRequestParam(request));
        logger.error("### exception: " + e.getMessage(), e);

        String header = request.getHeader("X-Requested-With");
        if ("XMLHttpRequest".equalsIgnoreCase(header)) {
            Map<String, Object> error = new HashMap<>();
            error.put("code", 1);
            error.put("message", ERROR_MESSAGE);
            writeJson(response, error);
            return null;
        } else {
            return new ModelAndView("error/500", "message", ERROR_MESSAGE);
        }

    }

    protected void writeJson(HttpServletResponse response, Map<String, Object> error) {

        try {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(JSON.toJSONString(error));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取请求参数中所有的信息
     *
     * @param request
     * @return
     */
    protected Map<String, String> getAllRequestParam(
            final HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iterator = requestParams.keySet().iterator(); iterator.hasNext(); ) {
            String name = iterator.next();
            String[] values = requestParams.get(name);
            StringBuilder valueStr = new StringBuilder();
            for (int i = 0; i < values.length; i++) {
                valueStr.append((i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",");
            }
            params.put(name, valueStr.toString());
        }
        return params;
    }

}
