package org.imooc.dao;

import java.util.List;

import org.imooc.bean.Ad;

public interface AdDao {
	 /**
     * ����
     * @param ad �������
     * @return Ӱ������
     */
    int insert(Ad ad);
    /**
     * ����ad����ģ����ѯ
     * @param ad 
     * @return �������
     */
	List<Ad> selectByPage(Ad ad);
	/**
     * ����ad������ѯ
     * @param ad 
     * @return һ������
     */
	Ad selectById(Long id);
	/**
     * ��������ɾ��
     * @param id ����
     * @return Ӱ������
     */
    int delete(Long id);
    /**
     * ����ad�޸�
     * @param ad
     * @return Ӱ������
     */
	int update(Ad ad);
}
