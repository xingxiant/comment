package org.imooc.controller.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.imooc.bean.Ad;
import org.imooc.bean.BusinessList;
import org.imooc.dto.AdDto;
import org.imooc.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//@Controller
@RestController
@RequestMapping("/api")
public class ApiController {
	@Autowired
	private AdService adService;

	@Value("${ad.number}")
	private int adNumber;

	//@ResponseBody  //��ʾ�÷����ķ��ؽ��ֱ��д��HTTP response body��,���ڷ���json����
	@RequestMapping(value="/homead",method=RequestMethod.GET)
	public List<AdDto> homead(){
		AdDto adDto=new AdDto();
		adDto.getPage().setPageNumber(adNumber);
		List<AdDto> ad=adService.searchByPage(adDto);
		return ad;

	}

	@RequestMapping(value="/homelist/{city}/{page}",method=RequestMethod.GET)
	public BusinessList homelist() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper=new ObjectMapper();
		String src="{\"hasMore\":true,\"data\":[{\"img\":\"http://images2015.cnblogs.com/blog/138012/201610/138012-20161016201638030-473660627.png\",\"title\":\"�������\",\"subTitle\":\"���Һ�����󣬻����ʿ�ζ\",\"price\":\"28\",\"distance\":\"120m\",\"mumber\":\"389\",\"id\":\"9685351454334126\"},{\"img\":\"http://images2015.cnblogs.com/blog/138012/201610/138012-20161016201645858-1342445625.png\",\"title\":\"������Դ����\",\"subTitle\":\"[����]�������\",\"price\":\"98\",\"distance\":\"140m\",\"mumber\":\"689\",\"id\":\"3156591375420923\"},{\"img\":\"http://images2015.cnblogs.com/blog/138012/201610/138012-20161016201652952-1050532278.png\",\"title\":\"��װ����\",\"subTitle\":\"ԭ��xxԪ���ּ�xxԪ�����޸�һ��\",\"price\":\"1980\",\"distance\":\"160\",\"mumber\":\"106\",\"id\":\"9282674228285897\"},{\"img\":\"http://images2015.cnblogs.com/blog/138012/201610/138012-20161016201700186-1351787273.png\",\"title\":\"��ɴ��Ӱ\",\"subTitle\":\"����Դ�����������\",\"price\":\"2899\",\"distance\":\"160\",\"mumber\":\"58\",\"id\":\"33948560512065207\"},{\"img\":\"http://images2015.cnblogs.com/blog/138012/201610/138012-20161016201708124-1116595594.png\",\"title\":\"����������\",\"subTitle\":\"˫������ײ͵�������\",\"price\":\"0\",\"distance\":\"160\",\"mumber\":\"1426\",\"id\":\"4848114667216299\"}]}";
		return mapper.readValue(src, new TypeReference<BusinessList>() {
		});



	}

	@RequestMapping(value="/submitComment",method=RequestMethod.POST)
	public Map<String,Object> submitComment() {
		Map<String,Object> result=new HashMap<String,Object>();
		result.put("errno", 0);
		result.put("msg", "ok");
		return result;


	}

}
