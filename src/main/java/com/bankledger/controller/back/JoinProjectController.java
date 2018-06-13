package com.bankledger.controller.back;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bankledger.controller.BaseController;
import com.bankledger.entity.Project;
import com.bankledger.entity.User;
import com.bankledger.mapper.JoinProjectMapper;
import com.bankledger.mapper.ProjectMapper;
import com.bankledger.service.IJoinProjectService;
import com.bankledger.utils.ResultHelper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * @AUTHOR: sandnul025
 * @MOTTO: Rainbow comes after a storm.
 * @DATE: 2018年6月5日 上午10:52:01
 */

@Controller
@RequestMapping("/join")
public class JoinProjectController extends BaseController{

	
	@Autowired
	IJoinProjectService joinProjectService;
	
	@Autowired
	JoinProjectMapper joinProjectMapper;
	
	@Autowired
	ProjectMapper projectMapper;
	
	
	@PostMapping("/add")
	@ResponseBody
	public ResultHelper add(String projectId, HttpServletRequest request) {
		
		if(StringUtils.isBlank(projectId)) {
			return ResultHelper.fail("缺少必要参数");
		}
		return joinProjectService.join(projectId, getUser(request).getBlockAddr());
	}
	
	
	
	/**
	 * 自己对自己参加的项目进行签到
	 * @param projectId
	 * @param request
	 * @return
	 */
	@PostMapping("/toSign")
	@ResponseBody
	public ResultHelper toSign(String projectId,HttpServletRequest request) {
		
		if(StringUtils.isBlank(projectId)) {
			return ResultHelper.fail("缺少必要参数");
		}
		return joinProjectService.toSign(projectId,getUser(request));
		
	}
	
	
	/**
	 * 获取自己  参加的活动
	 * @param request
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@PostMapping("/getListProject")
	@ResponseBody
	public Map<String, Object> getListProject( HttpServletRequest request,
			@RequestParam(name = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(name = "pageSize", required = false, defaultValue = "8") int pageSize){
		
		Page<Object> page = PageHelper.startPage(pageNum, pageSize);
		List<Project> projects = joinProjectMapper.getListProject(getUser(request).getBlockAddr());
		Map<String, Object> map = new HashMap<>();
        map.put("data", projects);
        map.put("count", page.getTotal());
        map.put("code", 0);
        map.put("message", "获取成功");
        return map;
	}
	
	
	/**
	 * 获取 自己发布活动所有的参加人员
	 * @param projectId
	 * @return
	 */
	@PostMapping("/getListPerson")
	@ResponseBody
	public ResultHelper getListPerson(String projectId,HttpServletRequest request) {
		
		if(StringUtils.isBlank(projectId)) {
			return ResultHelper.fail("缺少必要参数");
		}
		
		// 是否是自己的项目
		Project project = projectMapper.findOne(projectId, getUser(request).getBlockAddr());
		if(null == project) {
			return ResultHelper.fail("项目不存在");
		}
		
		List<User> users = joinProjectMapper.getListPerson(projectId);
		return ResultHelper.success(users);
	}
	
}
