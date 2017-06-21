package org.imooc.service;

import java.util.List;

import org.imooc.dto.AdDto;

public interface AdService {

	/**
	 * 新增广告
	 */
	public boolean add(AdDto adDto);
	public List<AdDto> searchByPage(AdDto adDto);
	public boolean remove(Long id);
	public AdDto getById(Long id);
	public boolean modify(AdDto adDto);
}
