package com.swx.ibms.common.utils;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

@SuppressWarnings("all")
public class BlobTypeHandler extends BaseTypeHandler<String> {

    @Override
    public String getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        String resStr = StringUtils.EMPTY;
        Blob blob = (Blob) rs.getBlob(columnName);
        if(blob!=null&&blob.length()>0) {
            int blobLength = (int) blob.length();
            if(blobLength>0){
                byte[] returnValue = null;
                if (null != blob) {
                    returnValue = blob.getBytes(1, blobLength);
                }
                try {
                    //将取出的流对象转为utf-8的字符串对象
                    resStr = new String(returnValue, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException("Blob Encoding Error!");
                }
            }
        }
        return resStr;
    }

    @Override
    public String getNullableResult(ResultSet arg0, int arg1) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getNullableResult(CallableStatement arg0, int arg1) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public void setNonNullParameter(PreparedStatement ps, int i,
                                    String parameter, JdbcType jdbcType) throws SQLException {
        //声明一个输入流对象
        ByteArrayInputStream bis;
        try {
            //把字符串转为字节流
            bis = new ByteArrayInputStream(parameter.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Blob Encoding Error!");
        }
        ps.setBinaryStream(i, bis, parameter.length());
    }


}
