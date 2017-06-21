package org.imooc.dao;

import java.util.List;

import org.imooc.bean.Ad;

public interface AdDao {
	 /**
     * 新增
     * @param ad 广告表对象
     * @return 影响行数
     */
    int insert(Ad ad);
    /**
     * 根据ad名字模糊查询
     * @param ad 
     * @return 多个广告表
     */
	List<Ad> selectByPage(Ad ad);
	/**
     * 根据ad主键查询
     * @param ad 
     * @return 一个广告表
     */
	Ad selectById(Long id);
	/**
     * 根据主键删除
     * @param id 主键
     * @return 影响行数
     */
    int delete(Long id);
    /**
     * 根据ad修改
     * @param ad
     * @return 影响行数
     */
	int update(Ad ad);
}
