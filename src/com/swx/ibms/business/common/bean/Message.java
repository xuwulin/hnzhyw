package com.swx.ibms.business.common.bean;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.UUID;

/**
 * Created by wh on 2017/11/15.
 */
public class Message {
    private static  String MSG_PORT = null;
    private static  String MSG_ADREE = null;
    private static  String MSG_PROTOCOL = null;

    public static void load(){
        ResourceBundle bl = ResourceBundle.getBundle("xxts");
        MSG_PORT = bl.getString("MSG_MY_PORT");
        MSG_ADREE = bl.getString("MSG_ADREE");
        MSG_PROTOCOL = bl.getString("MSG_MY_PROTOCOL");
    }
    private String message_id;  //消息消息唯一标识ID，UUID？
    private String state;        //消息修改状态标志 add/modify/delete
    private boolean read;       // 是否已读 true/false
    private String ex_user_id;  //外部系统中此消息所属用户
    private String title;        //消息标题
    private String summary;     //消息摘要
    private String msgsource;   //原始消息，备用
    private String date;        //时间
    private String protocol;   //协议 http、https
    private String ip;          //ip
    private String port;        //端口
    private String address;    //地址
    private String params;     //参数，供外部系统在做具体业务时作为判断依据


    public Message(){
        this.message_id = UUID.randomUUID().toString().replaceAll("\\-","");
    }


    public Message(String ex_user_id) throws UnknownHostException {
        if(MSG_ADREE==null||MSG_PORT==null||MSG_ADREE==null){
            load();
        }
        this.message_id = UUID.randomUUID().toString().replaceAll("\\-","");
        this.ex_user_id= ex_user_id;
        this.date= getStrTime(new Date());
        this.ip= InetAddress.getLocalHost().toString().split("\\/")[1];
        this.msgsource= "";
        this.protocol= MSG_PROTOCOL;
        this.port= MSG_PORT;
        this.address= MSG_ADREE;
        this.params= "";
    }

    public Message(String state,boolean read,String ex_user_id,String title,String summary,
                   Date date) throws UnknownHostException {
        if(MSG_ADREE==null||MSG_PORT==null||MSG_ADREE==null){
            load();
        }
        this.message_id = UUID.randomUUID().toString().replaceAll("\\-","");
        this.state= state;
        this.read= read;
        this.ex_user_id= ex_user_id;
        this.title= title;
        this.summary= summary;
        this.msgsource= "";
        this.date=getStrTime(date);
        this.protocol= MSG_PROTOCOL;
        this.ip= InetAddress.getLocalHost().toString().split("\\/")[1];
        this.port= MSG_PORT;
        this.address= MSG_ADREE;
        this.params= "";
    }


    public String getMessage_id() {
        return message_id;
    }

//    public void setMessage_id(String message_id) {
//        this.message_id = message_id;
//    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean getRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public String getEx_user_id() {
        return ex_user_id;
    }

    public void setEx_user_id(String ex_user_id) {
        this.ex_user_id = ex_user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getMsgsource() {
        return msgsource;
    }

    public void setMsgsource(String msgsource) {
        this.msgsource = msgsource;
    }

    public String getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date=getStrTime(date);
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getStrTime(Date date) {
        if(date==null){
            return null;
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }
}
