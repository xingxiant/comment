package org.imooc.dto;

import org.imooc.bean.Ad;
import org.springframework.web.multipart.MultipartFile;

public class AdDto extends Ad{

	private String img;   
	private MultipartFile imgFile;
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public MultipartFile getImgFile() {
		return imgFile;
	}
	public void setImgFile(MultipartFile imgFile) {
		this.imgFile = imgFile;
	}
}
