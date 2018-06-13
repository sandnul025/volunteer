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
import com.bankledger.mapper.ProjectMapper;
import com.bankledger.service.IProjectService;
import com.bankledger.utils.ResultHelper;
import com.bankledger.utils.Utils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * @AUTHOR: sandnul025
 * @MOTTO: Rainbow comes after a storm.
 * @DATE: 2018年6月4日 下午10:38:27
 */

@Controller
@RequestMapping("/project")
public class ProjectController extends BaseController{

	
	@Autowired
	private IProjectService service;
	
	@Autowired
	private ProjectMapper mapper;
	
	
	/**
	 * 发布项目
	 * @param project
	 * @param request
	 * @return
	 */
	
	@PostMapping("/add")
	@ResponseBody
	public ResultHelper add(Project project,HttpServletRequest request) {
		if(null == project) {
			return ResultHelper.fail("缺少必要参数");
		}
		if(StringUtils.isBlank(project.getCoin())) {
			return ResultHelper.fail("缺少必要参数积分");
		}
		if(StringUtils.isBlank(project.getDiscript())) {
			return ResultHelper.fail("缺少必要参数描述说明");
		}
		if(StringUtils.isBlank(project.getLimitCount())) {
			return ResultHelper.fail("缺少必要参数限制人数");
		}
		if(StringUtils.isBlank(project.getOrganize())) {
			return ResultHelper.fail("缺少必要参数组织");
		}
		if(StringUtils.isBlank(project.getLimitOrganizeFlag())) {
			return ResultHelper.fail("缺少必要参数人员是否限制组织内");
		}
		if(StringUtils.isBlank(project.getProjectName())) {
			return ResultHelper.fail("缺少必要参数活动名称");
		}
		if(StringUtils.isBlank(project.getProjectType())) {
			return ResultHelper.fail("缺少必要参数活动类型");
		}
		if(StringUtils.isBlank(project.getStartTime())) {
			return ResultHelper.fail("缺少必要参数开始时间");
		}
		if(StringUtils.isBlank(project.getEndTime())) {
			return ResultHelper.fail("缺少必要参数结束时间");
		}
		project.setBlockAddr(getUser(request).getBlockAddr());
		project.setCreated(Utils.getCreatetime());
		return service.add(project);
	}
	
	
	
	/**
	 * 发布者 提交活动 进行 审核
	 * @param projectId
	 * @param request
	 * @return
	 */
	@PostMapping("/submitAuth")
	@ResponseBody
	public ResultHelper submitAuth(String projectId,HttpServletRequest request) {
		
		if(StringUtils.isBlank(projectId)) {
			return ResultHelper.fail("缺少必要参数活动编号");
		}
		return service.submitAuth(projectId, getUser(request).getBlockAddr());
	}
	
	
	/**
	 * 发布者 结束活动
	 * @param projectId
	 * @param request
	 * @return
	 */
	@PostMapping("/offProject")
	@ResponseBody
	public ResultHelper offProject(String projectId,HttpServletRequest request) {
		
		if(StringUtils.isBlank(projectId)) {
			return ResultHelper.fail("缺少必要参数活动编号");
		}
		return service.offProject(projectId, getUser(request).getBlockAddr());
	}
	
	
	/**
	 * 管理员 审核通过 
	 * @param projectId
	 * @param request
	 * @return
	 */
	
	@PostMapping("/passAuth")
	@ResponseBody
	public ResultHelper passAuth(String projectId,HttpServletRequest request) {
		if(StringUtils.isBlank(projectId)) {
			return ResultHelper.fail("缺少必要参数活动编号");
		}
		
		/**
		 * 是否是管理员
		 * 1 普通用户
		 * 0 管理员
		 */
		
		User user = getUser(request);
		if(!user.getType().equals("0")) {
			return ResultHelper.fail("你不是管理员,无权限操作");
		}
		return service.passAuth(projectId);
	}
	
	
	/**
	 * 查询 项目列表   
	 * 用户 查询自己的
	 * @param request
	 * @param project
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	
	@PostMapping("/getListPoject")
	@ResponseBody
	public Map<String, Object> getListMine(HttpServletRequest request, Project project,
			 @RequestParam(name = "pageNum", required = false, defaultValue = "1") int pageNum,
	            @RequestParam(name = "pageSize", required = false, defaultValue = "8") int pageSize) {
		
		User user = getUser(request);
		
		// 普通用户 加上 blockaddr
		if(user.getType().equals("0")) {
			
		}else if(user.getType().equals("1")) {
			project.setBlockAddr(user.getBlockAddr());
		}else if(user.getType().equals("2")) {
			project.setStatus("2");
		}
		Page<Object> page = PageHelper.startPage(pageNum, pageSize);
		List<Project> projects = mapper.getList(project);
		Map<String, Object> map = new HashMap<>();
        map.put("data", projects);
        map.put("count", page.getTotal());
        map.put("code", 0);
        map.put("message", "获取成功");
        return map;
	}
	
	
	/**
	 * 获取审核的项目
	 * @param request
	 * @param project
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@PostMapping("/getListAuth")
	@ResponseBody
	public Map<String, Object> getListAuth(HttpServletRequest request, Project project,
			 @RequestParam(name = "pageNum", required = false, defaultValue = "1") int pageNum,
	            @RequestParam(name = "pageSize", required = false, defaultValue = "8") int pageSize) {
		Page<Object> page = PageHelper.startPage(pageNum, pageSize);
		List<Project> projects = mapper.getList(project);
		Map<String, Object> map = new HashMap<>();
       map.put("data", projects);
       map.put("count", page.getTotal());
       map.put("code", 0);
       map.put("message", "获取成功");
       return map;
	}
	
}
