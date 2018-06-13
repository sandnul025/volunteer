package com.bankledger.controller.back;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bankledger.controller.BaseController;
import com.bankledger.entity.CoinRecord;
import com.bankledger.entity.User;
import com.bankledger.mapper.CoinRecordMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * @AUTHOR: sandnul025
 * @MOTTO: Rainbow comes after a storm.
 * @DATE: 2018年6月5日 下午3:11:56
 */

@Controller
@RequestMapping("coin")
public class CoinRecordController extends BaseController {

	
	@Autowired
	CoinRecordMapper mapper;
	
	
	@PostMapping("getList")
	@ResponseBody
	public Map<String, Object> getListPublishing( HttpServletRequest request , CoinRecord record,
			@RequestParam(name = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(name = "pageSize", required = false, defaultValue = "8") int pageSize){
		
		
		// 查看自己的
		User user = getUser(request);
		if(user.getType().equals("0")) {
			// 管理员是 发给别人
			record.setBlockAddrFrom(user.getBlockAddr());;
		}else {
			// 普通用户只有打给自己
			record.setBlockAddrTo(user.getBlockAddr());
		}
		Page<Object> page = PageHelper.startPage(pageNum, pageSize);
		List<CoinRecord> records = mapper.getList(record);
		Map<String, Object> map = new HashMap<>();
        map.put("data", records);
        map.put("count", page.getTotal());
        map.put("code", 0);
        map.put("message", "获取成功");
        return map;
	}
	
}
