package com.swx.ibms.business.archives.bean;

import java.io.Serializable;

/**
 * 
 * PersonJl.java 个人经历信息实体类
 * @author east
 * @date<p>2017年5月16日</p>
 * @version 1.0
 * @description<p></p>
 */
@SuppressWarnings("all")
public class PersonJl implements Serializable {
	
	/**
	 * 序列化id
	 */
	private static final long UUID = 1L;
	
	/**
	 * 个人信息经历id
	 */
	private String id;
	
	/**
	 * 外键人员id(存入的是人员id，数据库并没有进行真正的外键关联)
	 */
	private String personId;
	
	/**
	 * 开始日期
	 */
	private String sDate;
	
	/**
	 * 结束日期
	 */
	private String eDate;
	
	/**
	 * 单位名称/学校名称
	 */
	private String name; 
	
	/**
	 * 地址
	 */
	private String adress;
	
	/**
	 * 职位名称/学校担任职位名称
	 */
	private String zwName;
	
	/**
	 * 类型（1：工作经历   2：教育经历  3 其他）
	 */
	private String type;

	/**
	 * 档案id
	 */
	private String daId;
	
	/**
	 * 工作部门，存放的是部门编码
	 */
	private String gzbm;
	
	/**
	 * 工作职责，存放的可能是很多文本
	 */
	private String gzzz;
	
	/**
	 * 
	 * @return 档案id
	 */
	public String getDaId() {
		return daId;
	}
	
	/**
	 * 
	 * @param daId 档案id
	 */
	public void setDaId(String daId) {
		this.daId = daId;
	}
	
	/**
	 * 
	 * @return 个人信息经历id
	 */
	public String getId() {
		return id;
	}
	

	/**
	 * 
	 * @param id 传入个人信息经历id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 
	 * @return 外键人员id
	 */
	public String getPersonId() {
		return personId;
	}

	/**
	 * 
	 * @param personId 传入外键人员id
	 */
	public void setPersonId(String personId) {
		this.personId = personId;
	}

	/**
	 * 
	 * @return 开始日期
	 */
	public String getsDate() {
		return sDate;
	}

	/**
	 * 
	 * @param sDate 传入开始日期
	 */
	public void setsDate(String sDate) {
		this.sDate = sDate;
	}

	/**
	 * 
	 * @return 结束日期
	 */
	public String geteDate() {
		return eDate;
	}

	/**
	 * 
	 * @param eDate 传入结束日期
	 */
	public void seteDate(String eDate) {
		this.eDate = eDate;
	}

	/**
	 * 
	 * @return 单位名称/学校名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name 单位名称/学校名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return 地址
	 */
	public String getAdress() {
		return adress;
	}

	/**
	 * 
	 * @param adress 传入地址
	 */
	public void setAdress(String adress) {
		this.adress = adress;
	}

	/**
	 * 
	 * @return 职位名称/学校担任职位名称
	 */ 
	public String getZwName() {
		return zwName;
	}

	/**
	 * 
	 * @param zwName 职位名称/学校担任职位名称
	 */
	public void setZwName(String zwName) {
		this.zwName = zwName;
	}

	/**
	 * 
	 * @return 类型（1：工作经历 2：教育经历 3 其他）
	 */
	public String getType() {
		return type;
	}

	/**
	 * 
	 * @param type 传入类型（1：工作经历 2：教育经历 3 其他）
	 */
	public void setType(String type) {
		this.type = type;
	}

	public String getGzbm() {
		return gzbm;
	}

	public void setGzbm(String gzbm) {
		this.gzbm = gzbm;
	}

	public String getGzzz() {
		return gzzz;
	}

	public void setGzzz(String gzzz) {
		this.gzzz = gzzz;
	}

	@Override
	public String toString() {
		return "PersonJl [id=" + id + ", personId=" + personId + ", sDate=" + sDate + ", eDate=" + eDate + ", name="
				+ name + ", adress=" + adress + ", zwName=" + zwName + ", type=" + type + ", daId=" + daId + ", gzbm="
				+ gzbm + ", gzzz=" + gzzz + "]";
	}
	
	
}
