package org.imooc.controller.content;

import javax.servlet.http.HttpServletRequest;

import org.imooc.bean.Ad;
import org.imooc.constant.PageCodeEnum;
import org.imooc.dto.AdDto;
import org.imooc.service.AdService;
import org.imooc.util.FileUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/ad")
public class AdController {
	@Autowired
	private AdService adService;
	/**
	 * 页面初始化
	 */
	@RequestMapping
	public String init(Model model, HttpServletRequest request) {
		AdDto adDto = new AdDto();
		model.addAttribute("list", adService.searchByPage(adDto));
		model.addAttribute("searchParam", adDto);
		return "/content/adList";
	}

	/**
	 * 新增页初始化
	 */
	@RequestMapping("/addInit")
	public String addInit() {
		return "/content/adAdd";
	}
	
	/**
	 * 新增
	 */
	@RequestMapping("/add")
	public String add(AdDto adDto, Model model) {
		if(adService.add(adDto)){
			model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.ADD_SUCCESS);
		}else{
			model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.ADD_FAIL);
		};
	
		return "forward:/ad";
	}
	
	/**
	 * 查询
	 */
	@RequestMapping("/search")
	public String search(Model model, AdDto adDto) {
		model.addAttribute("list", adService.searchByPage(adDto));
		model.addAttribute("searchParam", adDto);
		return "/content/adList";
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/remove")
	public String remove(@RequestParam("id")Long id,Model model) {
		
		if(adService.remove(id)) {
			model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.REMOVE_SUCCESS);
		} else {
			model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.REMOVE_FAIL);
		}
		return "forward:/ad";
	}
	/**
	 * 修改页面初始化
	 */
	@RequestMapping("/modifyInit")
	public String modifyInit(@RequestParam("id")Long id,Model model){
		model.addAttribute("modifyObj", adService.getById(id));
		return "/content/adModify";
	}
	/**
	 * 修改页面初始化
	 */
	@RequestMapping("/modify")
	public String modify(AdDto adDto){
		
		
		return "/content/adModify";
	}
}
