package org.imooc.controller.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.imooc.dto.AdDto;
import org.imooc.dto.BusinessDto;
import org.imooc.dto.BusinessListDto;
import org.imooc.service.AdService;
import org.imooc.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//@Controller
@RestController
@RequestMapping("/api")
public class ApiController {
	@Autowired
	private AdService adService;
	@Autowired
	private BusinessService businessService;

	@Value("${ad.number}")
	private int adNumber;
	
	@Value("${businessHome.number}")
	private int businessHomeNumber;

	@Value("${businessSearch.number}")
	private int businessSearchNumber;

	//@ResponseBody  //��ʾ�÷����ķ��ؽ��ֱ��д��HTTP response body��,���ڷ���json����
	@RequestMapping(value="/homead",method=RequestMethod.GET)
	public List<AdDto> homead(){
		AdDto adDto=new AdDto();
		adDto.getPage().setPageNumber(adNumber);
		List<AdDto> ad=adService.searchByPage(adDto);
		return ad;

	}
	
	/**
	 * ��ҳ-�Ƽ��б�����ϲ����
	 */
	@RequestMapping(value="/homelist/{city}/{page.currentPage}",method=RequestMethod.GET)
	public BusinessListDto homelist(BusinessDto businessDto) {
		businessDto.getPage().setPageNumber(businessHomeNumber);
		return businessService.searchByPageForApi(businessDto);
	}

	@RequestMapping(value="/submitComment",method=RequestMethod.POST)
	public Map<String,Object> submitComment() {
		Map<String,Object> result=new HashMap<String,Object>();
		result.put("errno", 0);
		result.put("msg", "ok");
		return result;


	}

}
