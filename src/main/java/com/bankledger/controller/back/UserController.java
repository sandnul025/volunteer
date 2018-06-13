package com.bankledger.controller.back;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bankledger.constant.Constant;
import com.bankledger.controller.BaseController;
import com.bankledger.entity.User;
import com.bankledger.mapper.UserMapper;
import com.bankledger.utils.HttpsUtils;
import com.bankledger.utils.ResultHelper;

/**
 * @AUTHOR: sandnul025
 * @MOTTO: Rainbow comes after a storm.
 * @DATE: 2018年6月4日 下午9:00:04
 */
@Controller
@RequestMapping
public class UserController extends BaseController{

	@Autowired
	private UserMapper userMapper;
	
	@PostMapping("register")
	@ResponseBody
	public ResultHelper register(User user) {
		
		int count = userMapper.isIdCard(user.getIdCard());
		if(count > 0) {
			return ResultHelper.fail("已存在该身份证号码");
		}
		
		// 创建用户 分配  地址
	  String host = "http://39.104.123.77:2254";
	  String auth = "Basic cnBjdXNlcjpycGNwYXNz";
	  Object[] params = { user.getIdCard(),user.getIdCard(),2,user.getIdCard() };
	  String method   = "registerapp";
	  logger.info(" ########## 调用底层Elt接口 - " + method + " - 开始 ##########");
	  
	  JSONObject p = new JSONObject();
	  p.put("method", method);
	  p.put("params", params == null ? new JSONArray() : JSONObject.toJSON(params));
	  Map<String, String> headerMap = new HashMap<>();
	  headerMap.put("Authorization", auth);
	  String response = null;
	  logger.info(" # Host :" + host);
	  logger.info(" # method :" + method);
	  logger.info(" # Params :" + p.getJSONArray("params"));
	  logger.info(" # Body   :" + p.toString());
	  try {
	      response = HttpsUtils.post(host, headerMap, p.toString());
	  } catch (Exception e) {
	      e.printStackTrace();
	  }
	  logger.info(" # Response :" + response);
	  logger.info(" ########## 调用底层Elt接口 - " + method + " - 结束 ##########");
	  Object resultDate = (JSONObject.parseObject(response)).get("result");
	  if(resultDate != null) {
		  
		  JSONObject result = (JSONObject)resultDate;
		  	user.setBlockAddr(result.get("adminSafeAddress").toString());
		  	user.setAppId(result.get("appId").toString());
		  	user.setTxId(result.get("txId").toString());
			user.setType("2");
			user.setCoinCount(new BigDecimal(0));
			userMapper.register(user);
			return ResultHelper.success("注册成功");
	  }else {
		  return ResultHelper.fail("注册失败");
	  }
	  
		
	}
	
	@PostMapping("login")
	@ResponseBody
	public ResultHelper login(User userVo,HttpServletRequest request) {
		
		
		User user = userMapper.login(userVo);
		if(null != user) {
			WebUtils.setSessionAttribute(request, Constant.login_session_key, user);
			return ResultHelper.success("登录成功");
		}
		return ResultHelper.fail("用户名或者密码错误");
	}
	
	@GetMapping("usercenter")
	public ModelAndView usercenter(HttpServletRequest request) {
		
		ModelAndView mv = new ModelAndView("usercenter");
		mv.addObject("userInfo", userMapper.getUserInfo(getUser(request).getIdCard()));
		mv.addObject("active", "usercenter");
		return mv;
	}
	
	
	@PostMapping("changeUser")
	@ResponseBody
	public ResultHelper changeUser(User userVo,HttpServletRequest request) {
		
		userVo.setBlockAddr(getUser(request).getBlockAddr());
		userMapper.changeUser(userVo);
		return ResultHelper.success("更新成功");
	}
	
	@PostMapping("open")
	@ResponseBody
	public ResultHelper open(HttpServletRequest request,String isopen) {
		
		userMapper.open(getUser(request).getIdCard(),isopen);
		return ResultHelper.success("操作成功");
	}
}
