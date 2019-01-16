package com.swx.ibms.business.etl.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 同步任务信息配置实体类
  id                 VARCHAR2(32) not null,
  clazz              VARCHAR2(30) not null,
  job_name           VARCHAR2(30) not null,
  job_group_name     VARCHAR2(30) not null,
  trigger_name       VARCHAR2(30) not null,
  trigger_group_name VARCHAR2(30),
  cron               VARCHAR2(30) not null,
  cjr_mc             VARCHAR2(30),
  cjr                VARCHAR2(10),
  cjsj               DATE not null,
  xgsj               DATE not null,
  status             NUMBER default 0,
  state              VARCHAR2(300),
  xh                 NUMBER
 * @author admin
 * 
 */
public class XtQuartzPz implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 3982321729812019210L;
	
	/**
	 * 主键id
	 */
	private String id;
	/**
	 * 执行操作类名以及路径/包名，例如：com.swx.XXX.XXX
	 */
	private String clazz;
	/**
	 * 任务名
	 */
	private String jobName;
	/**
	 * 任务组名
	 */
	private String jobGroupName;
	/**
	 * 触发器名称
	 */
	private String triggerName;
	/**
	 * 触发器组名
	 */
	private String triggerGroupName;
	/**
	 * 同步时间表达式。【格式：秒 分 时 日 月 周 年【选填】 ?代表未知】
	 */
	private String cron;
	/**
	 * 操作人名称
	 */
	private String cjrMc;
	/**
	 * 操作人【存储形式：dwbm_gh】
	 */
	private String cjr;
	/**
	 * 创建时间
	 */
	private Date cjsj;
	/**
	 * 修改时间
	 */
	private Date xgsj;
	/**
	 * 状态【保留字段，暂未使用】
	 */
	private Integer status;
	/**
	 * 说明
	 */
	private String state;
	/**
	 * 序号【排序用】
	 */
	private Integer xh;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobGroupName() {
		return jobGroupName;
	}
	public void setJobGroupName(String jobGroupName) {
		this.jobGroupName = jobGroupName;
	}
	public String getTriggerName() {
		return triggerName;
	}
	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}
	public String getTriggerGroupName() {
		return triggerGroupName;
	}
	public void setTriggerGroupName(String triggerGroupName) {
		this.triggerGroupName = triggerGroupName;
	}
	public String getCron() {
		return cron;
	}
	public void setCron(String cron) {
		this.cron = cron;
	}
	public String getCjrMc() {
		return cjrMc;
	}
	public void setCjrMc(String cjrMc) {
		this.cjrMc = cjrMc;
	}
	public String getCjr() {
		return cjr;
	}
	public void setCjr(String cjr) {
		this.cjr = cjr;
	}
	public Date getCjsj() {
		return cjsj;
	}
	public void setCjsj(Date cjsj) {
		this.cjsj = cjsj;
	}
	public Date getXgsj() {
		return xgsj;
	}
	public void setXgsj(Date xgsj) {
		this.xgsj = xgsj;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Integer getXh() {
		return xh;
	}
	public void setXh(Integer xh) {
		this.xh = xh;
	}
	@Override
	public String toString() {
		return "XtQuartzPz [id=" + id + ", clazz=" + clazz + ", jobName=" + jobName + ", jobGroupName=" + jobGroupName
				+ ", triggerName=" + triggerName + ", triggerGroupName=" + triggerGroupName + ", cron=" + cron
				+ ", cjrMc=" + cjrMc + ", cjr=" + cjr + ", cjsj=" + cjsj + ", xgsj=" + xgsj + ", status=" + status
				+ ", state=" + state + ", xh=" + xh + "]";
	}
	
	
}
