package com.bankledger.controller.back;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bankledger.constant.Constant;
import com.bankledger.controller.BaseController;
import com.bankledger.entity.Project;
import com.bankledger.entity.User;
import com.bankledger.mapper.ProjectMapper;

@Controller
public class PageController extends BaseController{

	@Autowired
	private ProjectMapper mapper;
	
	
    @GetMapping({"/login"})
    public String login() {
        return "back/login";
    }
    
    @GetMapping("loginOut")
    public String loginOut(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(Constant.login_session_key);
        }
        return "redirect:/";
    }
    
    @GetMapping({"/main"})
    public ModelAndView main(HttpServletRequest request) {
    	
    	ModelAndView mv = new ModelAndView("back/main");
    	User user = getUser(request);
    	if(user.getType().equals("0")) {
    		mv.addObject("count",mapper.authProjectCount());
    	}else {
    		mv.addObject("count",mapper.toSignProjectCount(user.getBlockAddr()));
    		mv.addObject("userInfo",user);
    	}
    	mv.addObject("active", "main");
        return mv;
    }
    
    @GetMapping({"/register"})
    public String register() {
        return "back/register";
    }
    
    @GetMapping({"/project/projectPage"})
    public ModelAndView projectPage() {
    	ModelAndView mv = new ModelAndView("back/project");
    	mv.addObject("active", "project");
        return mv;
    }
    
    @GetMapping({"/project/publish"})
    public ModelAndView publishPage() {
    	ModelAndView mv = new ModelAndView("back/publishProject");
    	mv.addObject("active", "publish");
        return mv;
    }
    
    @GetMapping({"/project/auth"})
    public ModelAndView authPage() {
    	ModelAndView mv = new ModelAndView("back/projectAuth");
    	mv.addObject("active", "auth");
        return mv;
    }
    
    @GetMapping({"/coin/coinPage"})
    public ModelAndView coinPage() {
    	ModelAndView mv = new ModelAndView("back/coin");
    	mv.addObject("active", "coin");
        return mv;
    }
    
    @GetMapping({"/join/joinProjectPage"})
    public ModelAndView joinProjectPage() {
    	ModelAndView mv = new ModelAndView("back/joinProject");
    	mv.addObject("active", "joinProject");
        return mv;
    }
    
    
    @GetMapping({"/"})
    public ModelAndView indexPage() {
    	ModelAndView mv = new ModelAndView("index");
        return mv;
    }
    
    /**
	 * 获取所有的 状态为 【进行中的】   可参加活动
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@GetMapping("/list")
	public ModelAndView getListPublishing( @RequestParam(name = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(name = "pageSize", required = false, defaultValue = "8") int pageSize){
		ModelAndView mv = new ModelAndView("back/project");
		Project project = new Project();
		// 可参加活动
		project.setStatus("2");
		List<Project> projects = mapper.getList(project);
		mv.addObject("projects", projects);
		mv.addObject("active", "project");
        return mv;
	}
	
	@GetMapping("/detail")
	public ModelAndView detail( String projectId){
		ModelAndView mv = new ModelAndView("detail");
		mv.addObject("project", mapper.getProjectByProjectId(projectId));
        return mv;
	}
	
	@GetMapping("/organize")
	public ModelAndView organize( String projectId){
		ModelAndView mv = new ModelAndView("organize");
		mv.addObject("organize", mapper.getProjectByProjectId(projectId));
		mv.addObject("active", "organize");
        return mv;
	}
	
	@GetMapping("/set")
	public ModelAndView set( HttpServletRequest request){
		
		ModelAndView mv = new ModelAndView("set");
		mv.addObject("active", "set");
		mv.addObject("userInfo", getUser(request));
        return mv;
	}
	
	@GetMapping("/error")
	public ModelAndView error( HttpServletRequest request){
		
		ModelAndView mv = new ModelAndView("error");
        return mv;
	}
}
