package com.swx.ibms.business.performance.bean;


import com.swx.ibms.common.utils.DateUtil;

import java.text.ParseException;
import java.util.Date;

/**
 * null
 *
 * @author admin
 * @date 2018-09-07
 */
public class XtGrjxKhrq {
    /**
     * 考核日期配置id
     */
    private String id;

    /**
     * 开始日期【只取月份-天数，eg:08-01】
     */
    private String ksrq;

    /**
     * 结束日期【只取月份-天数，eg:08-31】
     */
    private String jsrq;

    /**
     * 创建者，单位编码+工号，eg:“dwbm_gh”
     */
    private String createby;

    /**
     * 创建日期，格式“yyyy-MM-dd hh:mm:ss”
     */
    private Date createdate;

    /**
     * 更新者
     */
    private String updateby;

    /**
     * 更新日期
     */
    private Date updatedate;

    /**
     * 说明/描述
     */
    private String remarks;

    /**
     * 排序的序号，默认数字0
     */
    private Integer xh;

    /**
     * 删除标识
     */
    private String delflag;

    /**
     * 考核最后期限
     */
    private String deadline;

    public String getId () {
        return id;
    }

    public void setId (String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getKsrq () {
        return ksrq;
    }

    public void setKsrq (String ksrq) {
        this.ksrq = ksrq == null ? null : ksrq.trim();
    }

    public String getJsrq () {
        return jsrq;
    }

    public void setJsrq (String jsrq) {
        this.jsrq = jsrq == null ? null : jsrq.trim();
    }

    public String getCreateby () {
        return createby;
    }

    public void setCreateby (String createby) {
        this.createby = createby == null ? null : createby.trim();
    }

    public Date getCreatedate () {
        return createdate;
    }

    public void setCreatedate (Date createdate) {
        this.createdate = createdate;
    }

    public String getUpdateby () {
        return updateby;
    }

    public void setUpdateby (String updateby) {
        this.updateby = updateby == null ? null : updateby.trim();
    }

    public Date getUpdatedate () {
        return updatedate;
    }

    public void setUpdatedate (Date updatedate) {
        this.updatedate = updatedate;
    }

    public String getRemarks () {
        return remarks;
    }

    public void setRemarks (String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Integer getXh () {
        return xh;
    }

    public void setXh (Integer xh) {
        this.xh = xh;
    }

    public String getDelflag () {
        return delflag;
    }

    public void setDelflag (String delflag) {
        this.delflag = delflag == null ? null : delflag.trim();
    }


    public String getDeadline () {
        return deadline;
    }

    public void setDeadline (String deadline) {
        this.deadline = deadline;
    }

    /**
     * 获取开始日期【只取月份-天数，eg:08-01】
     *
     * @return
     */
    public String getStartDateExcludeYear () {
        return getKsrq().substring(4);
    }

    /**
     * 获取结束日期【只取月份-天数，eg:08-31】
     *
     * @return
     */
    public String getEndDateExcludeYear () {
        return getJsrq().substring(4);
    }


    /**
     * 是否超期
     * @return
     * @throws ParseException
     */
    public boolean isOverdue () throws ParseException {
        //
        String handle = DateUtil.getYear(new Date()) + deadline.substring(4);

        Date deadlineDate = DateUtil.stringtoDate(handle, "yyyy-MM-dd");
        long now = System.currentTimeMillis();

        return now - deadlineDate.getTime() > 0;

    }
}