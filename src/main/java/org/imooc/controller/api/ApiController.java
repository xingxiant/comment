package org.imooc.controller.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.imooc.constant.ApiCodeEnum;
import org.imooc.dto.AdDto;
import org.imooc.dto.ApiCodeDto;
import org.imooc.dto.BusinessDto;
import org.imooc.dto.BusinessListDto;
import org.imooc.dto.OrderForBuyDto;
import org.imooc.dto.OrdersDto;
import org.imooc.service.AdService;
import org.imooc.service.BusinessService;
import org.imooc.service.MemberService;
import org.imooc.service.OrdersService;
import org.imooc.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//@Controller
@RestController
@RequestMapping("/api")
public class ApiController {
	@Autowired
	private AdService adService;
	@Autowired
	private BusinessService businessService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private OrdersService ordersService;

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

	/**
	 * �������ҳ - ������� - ��������
	 */
	@RequestMapping(value = "/search/{page.currentPage}/{city}/{category}/{keyword}", method = RequestMethod.GET)
	public BusinessListDto searchByKeyword(BusinessDto businessDto) {
		businessDto.getPage().setPageNumber(businessSearchNumber);
		return businessService.searchByPageForApi(businessDto);
	}
	/**
	 * �������ҳ - ������� - ��������
	 */
	@RequestMapping(value ="/search/{page.currentPage}/{city}/{category}", method = RequestMethod.GET)
	public BusinessListDto search(BusinessDto businessDto) {
		businessDto.getPage().setPageNumber(businessSearchNumber);
		return businessService.searchByPageForApi(businessDto);
	}
	@RequestMapping(value="/submitComment",method=RequestMethod.POST)
	public Map<String,Object> submitComment() {
		Map<String,Object> result=new HashMap<String,Object>();
		result.put("errno", 0);
		result.put("msg", "ok");
		return result;
	}
	
	/**
	 * ����ҳ-�̻���Ϣ
	 */
	@RequestMapping(value="/detail/info/{id}",method=RequestMethod.GET)
	public BusinessDto detail(@PathVariable("id")Long id){
		return businessService.getById(id);
	}
	
	/**
	 * �����ֻ����·�������֤��
	 */
	@RequestMapping(value = "/sms", method = RequestMethod.POST)
	public ApiCodeDto sms(@RequestParam("username") Long username) {
		ApiCodeDto dto;
		// 1����֤�û��ֻ����Ƿ���ڣ��Ƿ�ע�����
		if (memberService.exists(username)) {
			// 2������6λ�����
			String code = String.valueOf(CommonUtil.random(6));
			// 3�������ֻ������Ӧ��md5(6λ�����)��һ�㱣��1���ӣ�1���Ӻ�ʧЧ��
			if (memberService.saveCode(username, code)) {
				// 4�����ö���ͨ����������6λ��������͵���Ӧ���ֻ��ϡ�
				if (memberService.sendCode(username, code)) {
					dto = new ApiCodeDto(ApiCodeEnum.SUCCESS.getErrno(), code);
				} else {
					dto = new ApiCodeDto(ApiCodeEnum.SEND_FAIL);
				}
			} else {
				dto = new ApiCodeDto(ApiCodeEnum.REPEAT_REQUEST);
			}
		} else {
			dto = new ApiCodeDto(ApiCodeEnum.USER_NOT_EXISTS);
		}
		return dto;
	}

	/**
	 * ��Ա��¼
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ApiCodeDto login(@RequestParam("username") Long username, @RequestParam("code") String code) {
		ApiCodeDto dto;
		// 1�����ֻ���ȡ�������md5(6λ�����)����ȡ�����������ύ������codeֵ��ͬΪУ��ͨ��
		String saveCode = memberService.getCode(username);
		if (saveCode != null) {
			if (saveCode.equals(code)) {
				// 2�����У��ͨ��������һ��32λ��token
				String token = CommonUtil.getUUID();
				// 3�������ֻ������Ӧ��token��һ������ֻ�����;û�������˽���������£�����10���ӣ�
				memberService.saveToken(token, username);
				// 4�������token���ظ�ǰ��
				dto = new ApiCodeDto(ApiCodeEnum.SUCCESS);
				dto.setToken(token);
			} else {
				dto = new ApiCodeDto(ApiCodeEnum.CODE_ERROR);
			}
		} else {
			dto = new ApiCodeDto(ApiCodeEnum.CODE_INVALID);
		}
		return dto;
	}
	
	/**
	 * ��
	 */
	@RequestMapping(value = "/order", method = RequestMethod.POST)
	public ApiCodeDto order(OrderForBuyDto orderForBuyDto) {
		ApiCodeDto dto;
		// 1��У��token�Ƿ���Ч���������Ƿ��������һ��token�����Ҷ�Ӧ��ŵĻ�Ա��Ϣ������ָ�����ֻ��ţ����ύ��������Ϣһ�£�
		Long phone = memberService.getPhone(orderForBuyDto.getToken());
		if (phone != null && phone.equals(orderForBuyDto.getUsername())) {
			// 2�������ֻ��Ż�ȡ��Ա����
			Long memberId = memberService.getIdByPhone(phone);
			// 3�����涩��
			OrdersDto ordersDto = new OrdersDto();
			ordersDto.setNum(orderForBuyDto.getNum());
			ordersDto.setPrice(orderForBuyDto.getPrice());
			ordersDto.setBusinessId(orderForBuyDto.getId());
			ordersDto.setMemberId(memberId);
			ordersService.add(ordersDto);
			dto = new ApiCodeDto(ApiCodeEnum.SUCCESS);
			// 4��TODO ����һ����Ҫ����δ��-->������Ʒ���ѹ����������� �������������������spring task ��ʱ�������ݿ�Ĳ�ѯ�����
		} else {
			dto = new ApiCodeDto(ApiCodeEnum.NOT_LOGGED);
		}
		return dto;
	}
	
	/**
	 * �����б�
	 */
	@RequestMapping(value = "/orderlist/{username}", method = RequestMethod.GET)
	public List<OrdersDto> orderlist(@PathVariable("username") Long username) {
		// �����ֻ���ȡ����ԱID
		Long memberId = memberService.getIdByPhone(username);
		return ordersService.getListByMemberId(memberId);
	}

}
