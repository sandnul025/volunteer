package com.bankledger.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bankledger.config.ConfigProperties;
import com.bankledger.entity.CoinRecord;
import com.bankledger.entity.Project;
import com.bankledger.entity.User;
import com.bankledger.mapper.CoinRecordMapper;
import com.bankledger.mapper.JoinProjectMapper;
import com.bankledger.mapper.ProjectMapper;
import com.bankledger.mapper.UserMapper;
import com.bankledger.service.IProjectService;
import com.bankledger.utils.HttpsUtils;
import com.bankledger.utils.ResultHelper;
import com.bankledger.utils.SnowflakeIdWorker;
import com.bankledger.utils.Utils;

/**
 * @AUTHOR: sandnul025
 * @MOTTO: Rainbow comes after a storm.
 * @DATE: 2018年6月5日 上午9:13:17
 */

@Service
public class ProjectServiceImpl implements IProjectService{

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ConfigProperties properties;
	
	@Autowired
	private ProjectMapper projectMapper;
	
	@Autowired
	CoinRecordMapper coinRecordMapper;
	
	@Autowired
	JoinProjectMapper joinProjectMapper;
	
	@Autowired
	UserMapper userMapper;
	
	SnowflakeIdWorker work = new SnowflakeIdWorker(4, 0);
	
	@Override
	public ResultHelper add(Project project) {
		
		project.setProjectId(work.nextId()+"");
		project.setStatus("0");
		
		
		
		
		// rpc 发布项目
				String host =properties.getRpcHost();
			    String auth = "Basic cnBjdXNlcjpycGNwYXNz";
				  JSONObject data = new JSONObject();
				  // 参数
				  data.put("projectId", project.getProjectId());
				  data.put("projectType", project.getProjectType());
				  data.put("projectName", project.getProjectName());
				  data.put("organize", project.getOrganize());
				  data.put("startTime", project.getStartTime());
				  data.put("endTime", project.getEndTime());
				  data.put("address", project.getAddress());
				  data.put("limitCount", project.getLimitCount());
				  data.put("coin", project.getCoin());
				  data.put("limitOrganizeFlag", project.getLimitOrganizeFlag());
				  data.put("discript", project.getDiscript());
				  data.put("created", project.getCreated());
				  data.put("status", project.getStatus());
				  data.put("sendType", "1");
				  
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
					  project.setTxId(txId);
					  projectMapper.add(project);
					  return ResultHelper.success("发布活动成功");
				  }else {
					  return ResultHelper.fail("发布活动失败");
				  }
		
	}
	
	@Override
	public ResultHelper submitAuth(String projectId, String blockAddr) {
		/**
		 * 1.是否是自己的项目
		 * 2.该项目目前的状态是否为 新建状态0
		 * 3.更新状态为1
				// 0新建  项目创建 成功
				// 1待审核 由发布者点击提交审核  管理员进行审核
				// 2进行中
				// 3结束
		 * 
		 */
		Project project = projectMapper.findOne(projectId, blockAddr);
		if(null == project) {
			return ResultHelper.fail("项目不存在");
		}
		
		if(!project.getStatus().equals("0")) {
			return ResultHelper.fail("该项目的状态不正确");
		}
		projectMapper.updateStatus(projectId, blockAddr, "1");
		
		return ResultHelper.success("提交审核成功");
	}
	
	
	
	/**
	 * 发布者 结束项目
	 */
	@Transactional
	@Override
	public ResultHelper offProject(String projectId, String blockAddr) {
		Project project = projectMapper.findOne(projectId, blockAddr);
		if(null == project) {
			return ResultHelper.fail("项目不存在");
		}
		
		if(!project.getStatus().equals("2")) {
			return ResultHelper.fail("该项目的状态不正确");
		}
		int i = projectMapper.updateStatus(projectId, blockAddr, "3");
		if( i == 1) {
			// 发送积分
			List<User> users =  joinProjectMapper.getListPerson(projectId);
			CoinRecord record = null;
			String blockAddrFrom = properties.getAdminAddress();
			String created = Utils.getCreatetime();
			for (User user : users) {
				if(user.getIsCome().equals("1")) {	
					
					// rpc 发币
					String host =properties.getRpcHost();
				      String auth = "Basic cnBjdXNlcjpycGNwYXNz";
				      Object[] params = { properties.getAdminAddress(), project.getCoin()};
				      String method   = "sendtoaddress";
				      long start = System.currentTimeMillis();
				      logger.info(" ########## 调用底层Elt接口 - " + method + " - 开始 ##########");
				      JSONObject p = new JSONObject();
				      p.put("method", method);
				      p.put("params", params == null ? new JSONArray() : JSONArray.toJSON(params));
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
						  String txId = resultDate.toString();
						// 插入记录
							record = new CoinRecord();
							record.setBlockAddrFrom(blockAddrFrom);
							record.setBlockAddrTo(user.getBlockAddr());
							record.setCoinNum(project.getCoin());
							record.setProjectId(projectId);
							record.setProjectName(project.getProjectName());
							record.setAddress(project.getAddress());
							record.setOrganize(project.getOrganize());
							record.setCreated(created);
							record.setTxId(txId);
							// 增加加钱记录
							coinRecordMapper.insert(record);
							// 更新钱包加
							userMapper.updateCoinAdd(user.getBlockAddr(),project.getCoin());
							// 更新钱包减
							userMapper.updateCoinSubtract(blockAddrFrom,project.getCoin());
					  }
				}
			}
			
			
			// rpc 接口 结束项目
			String host = properties.getRpcHost();
		    String auth = "Basic cnBjdXNlcjpycGNwYXNz";
			  JSONObject data = new JSONObject();
			  // 参数
			  data.put("projectId", project.getProjectId());
			  data.put("sendType", "3");
			  
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
				  projectMapper.updateStatusByProjectId(projectId, "3");
			  }else {
				  return ResultHelper.fail("发布活动失败");
			  }
		}
		return ResultHelper.success("结束活动成功");
	}
	

	@Override
	public ResultHelper passAuth(String projectId) {
		/**
		 * 1.判断 该用户 是否是管理员
		 * 2.项目是否存在
		 * 2.项目状态 是否是待审核
		 * 3.更新项目状态为 进行中
		 */
		Project project = projectMapper.getProjectByProjectId(projectId);
		if(!project.getStatus().equals("1")) {
			return ResultHelper.fail("该活动的状态不正确");
		}
		
		

		// rpc 接口  审核通过
					String host = properties.getRpcHost();
				    String auth = "Basic cnBjdXNlcjpycGNwYXNz";
					  JSONObject data = new JSONObject();
					  // 参数
					  data.put("projectId", project.getProjectId());
					  data.put("sendType", "4");
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
						  projectMapper.updateStatusByProjectId(projectId, "2");
					  }else {
						  return ResultHelper.fail("审核通过失败");
					  }
		return ResultHelper.success("审核通过");
	}

}
