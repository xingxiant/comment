package org.imooc.controller.system;

import org.imooc.dto.AdDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index")
public class IndexController {
	/**
	 * ��ʾ��ҳ
	 * @return
	 */
	@RequestMapping
	public String init(){
		return "/system/index";
	}
	
}
