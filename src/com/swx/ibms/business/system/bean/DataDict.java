package com.swx.ibms.business.system.bean;

import java.util.Date;

/**
 * Created by Try on 2017/11/6.
 * 数据字典实体类
 */
public class DataDict {
    /**
     * 数据字典id
     */
    private String dictId;
    /**
     * 数据字典名称
     */
    private String dictName;
    /**
     * 数据字典类型
     */
    private String dictType;
    /**
     * 数据字典类型值
     */
    private String dictTypeValue;
    /**
     * 数据字典父id
     */
    private String dictFid;
    /**
     * 数据字典操作单位编码
     */
    private String dictOperateDwbm;
    /**
     * 数据字典操作人(应该是装工号，这儿暂时装的是人名)
     */
    private String dictOperator;
    /**
     *  创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date modifyTime;
    /**
     * 数据字典状态（预留字段），默认为'0'【数字零】
     */
    private String dictStatus;
    /**
     * 数据字典说明
     */
    private String dictExplain;
    /**
     * 数据字典标识
     */
    private String dictIdentifying;


	@Override
    public String toString() {
        return "DataDict{" +
                "dictId='" + dictId + '\'' +
                ", dictName='" + dictName + '\'' +
                ", dictType='" + dictType + '\'' +
                ", dictTypeValue='" + dictTypeValue + '\'' +
                ", dictFid='" + dictFid + '\'' +
                ", dictOperateDwbm='" + dictOperateDwbm + '\'' +
                ", dictOperator='" + dictOperator + '\'' +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", dictStatus='" + dictStatus + '\'' +
                ", dictExplain='" + dictExplain + '\'' +
                ", dictIdentifying='" + dictIdentifying + '\'' +
                '}';
    }

    public String getDictId() {
        return dictId;
    }

    public void setDictId(String dictId) {
        this.dictId = dictId;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public String getDictTypeValue() {
        return dictTypeValue;
    }

    public void setDictTypeValue(String dictTypeValue) {
        this.dictTypeValue = dictTypeValue;
    }

    public String getDictFid() {
        return dictFid;
    }

    public void setDictFid(String dictFid) {
        this.dictFid = dictFid;
    }

    public String getDictOperateDwbm() {
        return dictOperateDwbm;
    }

    public void setDictOperateDwbm(String dictOperateDwbm) {
        this.dictOperateDwbm = dictOperateDwbm;
    }

    public String getDictOperator() {
        return dictOperator;
    }

    public void setDictOperator(String dictOperator) {
        this.dictOperator = dictOperator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getDictStatus() {
        return dictStatus;
    }

    public void setDictStatus(String dictStatus) {
        this.dictStatus = dictStatus;
    }

    public String getDictExplain() {
        return dictExplain;
    }

    public void setDictExplain(String dictExplain) {
        this.dictExplain = dictExplain;
    }
    public String getDictIdentifying() {
		return dictIdentifying;
	}

	public void setDictIdentifying(String dictIdentifying) {
		this.dictIdentifying = dictIdentifying;
	}
}
