package org.imooc.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.imooc.bean.Ad;
import org.imooc.dao.AdDao;
import org.imooc.dto.AdDto;
import org.imooc.service.AdService;
import org.imooc.util.FileUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AdServiceImpl implements AdService{
	
	//定义dao
	@Autowired
	private AdDao adDao;
	//读取properties文件的文件保存文件夹
	@Value("${adImage.savePath}")
	private String adImageSavePath;
	
	@Value("${adImage.url}")
	private String adImageUrl;
	
	public boolean add(AdDto adDto) {
		Ad ad=new Ad();
		ad.setTitle(adDto.getTitle());
		ad.setLink(adDto.getLink());
		ad.setWeight(adDto.getWeight());
		String fileName=System.currentTimeMillis()+"_"+adDto.getImgFile().getOriginalFilename();
		if(adDto.getImgFile()!=null&&adDto.getImgFile().getSize()>0){
			File fileFolder=new File(adImageSavePath);
			File file=new File(adImageSavePath+fileName);
			if(!fileFolder.exists()){
				fileFolder.mkdirs();
			}
			try {
				adDto.getImgFile().transferTo(file);
				ad.setImgFileName(fileName);
				adDao.insert(ad);
				return true;
			} catch (IllegalStateException e) {
				//TODO 添加错误日志
				return false;
			} catch (IOException e) {
				return false;
		    }
		}else{
			return false;
		}
	}

	public List<AdDto> searchByPage(AdDto adDto) {
		List<AdDto> result=new ArrayList<AdDto>();
		Ad ad=new Ad();
		BeanUtils.copyProperties(adDto, ad);
		List<Ad> adList=adDao.selectByPage(ad);
		for(Ad adTemp : adList){
			AdDto adDtoTemp=new AdDto();
			BeanUtils.copyProperties(adTemp, adDtoTemp);
			adDtoTemp.setImg(adImageUrl+adTemp.getImgFileName());
			result.add(adDtoTemp);
		}
		return result;
	}

	@Override
	public boolean remove(Long id) {
		Ad ad=adDao.selectById(id);
		int deleteRows=adDao.delete(id);
		FileUtil.delete(adImageSavePath+ad.getImgFileName());
		return deleteRows==1;
	}

	@Override
	public AdDto getById(Long id) {
		AdDto result=new AdDto();
		Ad ad=adDao.selectById(id);
	    BeanUtils.copyProperties(ad, result);
	    result.setImg(ad.getImgFileName());
	    return result;
		 
	}
	public boolean modify(AdDto adDto){
		
		adDto.setImgFileName(adDao.selectById(adDto.getId()).getImgFileName());
		Ad ad=new Ad();
		
		BeanUtils.copyProperties(adDto, ad);
		String fileName=null;
		if(adDto.getImgFile()!=null&&adDto.getImgFile().getSize()>0){
			try {
				fileName=FileUtil.save(adDto.getImgFile(), adImageSavePath);
				ad.setImgFileName(fileName);
				
			} catch (IllegalStateException e) {
				//TODO 需要打印日志
				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			}	
		}
		int updateCount = adDao.update(ad);
		if (updateCount != 1) {
			return false;
		}
		if (fileName != null) {
			return FileUtil.delete(adImageSavePath + adDto.getImgFileName());
		}
		return true;
		
	}
}
