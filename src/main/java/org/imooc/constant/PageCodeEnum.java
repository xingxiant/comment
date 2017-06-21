package org.imooc.constant;

public enum PageCodeEnum {
	ADD_SUCCESS(1000,"�����ɹ���"),
    ADD_FAIL(1001,"����ʧ�ܣ�"),
    MODIFY_SUCCESS(1100,"�޸ĳɹ���"),
    MODIFY_FAIL(1101,"�޸�ʧ�ܣ�"),
    REMOVE_SUCCESS(1200,"ɾ���ɹ���"),
    REMOVE_FAIL(1201,"ɾ��ʧ�ܣ�"),
    LOGIN_FAIL(1301,"��¼ʧ�ܣ��û����������"),
    SESSION_TIMEOUT(1302,"session��ʱ�������µ�¼��"),
    NO_AUTH(1303,"û��Ȩ�޷���������Դ�����л��˻������ԣ�"),
    USERNAME_EXISTS(1401,"�û����Ѵ��ڣ�"),
    GROUPNAME_EXISTS(1402,"�û������Ѵ��ڣ�"),
    SUB_MENU_EXISTS(1403,"�˵��»������Ӳ˵���"),
    ASSIGN_SUCCESS(1500,"����ɹ���"),
    ASSIGN_FAIL(1501,"����ʧ�ܣ�"),
    ORDER_SUCCESS(1600,"����ɹ���"),
    ORDER_FAIL(1601,"����ʧ�ܣ�"),;
	private Integer code;
	private String msg;
	
	public static final String KEY="pageCode";
	PageCodeEnum(Integer code,String msg) {
		this.code=code;
		this.msg=msg;
		
	}
	public Integer getCode() {
		return code;
	}
	public String getMsg() {
		return msg;
	}
}
