package org.imooc.controller.api;

import java.util.List;

import org.imooc.bean.Ad;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/api")
public class ApiController {
	@ResponseBody  //表示该方法的返回结果直接写入HTTP response body中
	@RequestMapping(value="/homead",method=RequestMethod.GET)
	public List<Ad> homead() {
		ObjectMapper mapper=new ObjectMapper();
		//mapper.readValue(src, valueType)
		return null;
		

	}

}
