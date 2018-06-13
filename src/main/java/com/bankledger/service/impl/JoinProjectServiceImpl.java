package com.bankledger.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bankledger.config.ConfigProperties;
import com.bankledger.entity.Project;
import com.bankledger.entity.User;
import com.bankledger.mapper.JoinProjectMapper;
import com.bankledger.mapper.ProjectMapper;
import com.bankledger.service.IJoinProjectService;
import com.bankledger.utils.HttpsUtils;
import com.bankledger.utils.ResultHelper;
import com.bankledger.utils.Utils;

/**
 * @AUTHOR: sandnul025
 * @MOTTO: Rainbow comes after a storm.
 * @DATE: 2018年6月5日 上午11:05:03
 */

@Service
public class JoinProjectServiceImpl implements IJoinProjectService{

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ConfigProperties properties;
	
	@Autowired
	JoinProjectMapper joinProjectMapper;
	
	@Autowired
	ProjectMapper projectMapper;
	
	@Override
	public ResultHelper join(String projectId, String blockAddr) {
		/**
		 * 参加 项目 
		 *  项目是否存在
		 *  是否已参赛
		 *  项目状态是否正确
		 */
		Project project = projectMapper.getProjectByProjectId(projectId);
		if(null == project) {
			return ResultHelper.fail("项目不存在");
		}
		if(!project.getStatus().equals("2")) {
			return ResultHelper.fail("活动状态不对，无法参加该活动");
		}
		if( joinProjectMapper.isExist(projectId,blockAddr) > 0) {
			return ResultHelper.fail("已经参加该活动");
		}
		// 默认没有签到
		joinProjectMapper.join(projectId, blockAddr,Utils.getCreatetime(),"0");
		return ResultHelper.success("参加活动成功");
	}

	
	/**
	 * 签到
	 */
	@Override
	public ResultHelper toSign(String projectId, User user) {
		
		
		Project project = projectMapper.getProjectByProjectId(projectId);
		if(null == project) {
			return ResultHelper.fail("项目不存在");
		}
		if(!project.getStatus().equals("2")) {
			return ResultHelper.fail("活动状态不对，无法参加该活动");
		}
		if( joinProjectMapper.isExist(projectId,user.getBlockAddr()) == 0) {
			return ResultHelper.fail("未参加该活动");
		}
		
		// rpc 接口  签到
		String host = properties.getRpcHost();
	    String auth = "Basic cnBjdXNlcjpycGNwYXNz";
		  JSONObject data = new JSONObject();
		  // 参数
		  data.put("projectId", projectId);
		  data.put("userInfo", user);
		  data.put("sendType", "2");
		  
		  Object[] params = { 1,properties.getAdminAddress(),properties.getAdminAddressAppId(),1000, Utils.str2HexStr(JSONObject.toJSONString(data))};
		  String method   = "createextenddatatx";
		  long start = System.currentTimeMillis();
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
		  logger.info(" # use time :" + (System.currentTimeMillis()-start)+"");
		  logger.info(" ########## 调用底层Elt接口 - " + method + " - 结束 ##########");
		  Object resultDate = (JSONObject.parseObject(response)).get("result");
		  if(resultDate != null) {
			  JSONObject result = (JSONObject)resultDate;
			  String txId = result.get("txId").toString();
		  joinProjectMapper.toSign(projectId,user.getBlockAddr(),"1",txId);
		  return ResultHelper.success("签到成功");
		  }else {
			  return ResultHelper.fail("签到失败");
		  }
		
	}
}
