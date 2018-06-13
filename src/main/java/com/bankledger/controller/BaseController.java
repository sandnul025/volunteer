package com.bankledger.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import com.bankledger.constant.Constant;
import com.bankledger.entity.User;
import com.bankledger.mapper.UserMapper;

/**
 * @AUTHOR: sandnul025
 * @MOTTO: Rainbow comes after a storm.
 * @DATE: 2018年4月8日 下午5:18:19
 */
@Component
public class BaseController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserMapper userMapper;
    
    protected User getUser(HttpServletRequest request) {
        
    	User user = (User)WebUtils.getSessionAttribute(request, Constant.login_session_key);
        return userMapper.getUserInfo(user.getIdCard());
    }

}
