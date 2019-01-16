package com.swx.ibms.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import sun.misc.BASE64Decoder;

/**
 * Created by wh on 2017/11/14.
 */
@SuppressWarnings("all")
public class FileUtil {

    public static  List<Object>  getFiles(HttpServletRequest request){
        String path = request.getSession().getServletContext().getRealPath("/view");
        File file = new File(path);
        Map<String,Object> map = new HashMap<>();
        List<Object> list = new ArrayList<>();
        findAllFile(file,list);
        return list;
    }

    /**
     * 删除子目录，包括指定的文件夹
     *
     * @param file
     */
    public static void findAllFile(File file,List<Object> list) {
        if (file.isDirectory()) {
            Map<String,Object> childrenMap = new HashMap<>();
            List<Object> list2 = new ArrayList<>();
            childrenMap.put(file.getName(),list2);
            list.add(childrenMap);
            String[] children = file.list();
            //递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++) {
                findAllFile(new File(file, children[i]),list2);
            }
        }else {
            list.add(file.getName());
        }

    }

    /**
     * 将图片转化为base64加密后的字符串
     * @param file 二进制文件
     * @return 返回Base64编码过的字节数组字符串
     */
    public static String getBase64ImgStr(MultipartFile file) {
//    	InputStream input = null;
        byte[] data = null;
        String base64Data = StringUtils.EMPTY;
        try {
//    		input = file.getInputStream();
//    		data = new byte[input.available()];
//    		input.read(data);
//    		input.close();
            data = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //对字节数组Base64编码
        Base64 encoder = new Base64();
        base64Data = encoder.encodeBase64String(data);
        return base64Data;//返回Base64编码过的字节数组字符串
    }


    /**
     * //base64字符串转化成图片
     * @param imgStr base64转化后的图片字符串
     * @param imgFilePath 转存的图片地址、名称、后缀。
     * @return 是否转化成功
     */
    public static boolean GenerateImage(String imgStr,String imgFilePath) {
        boolean res = false;
        //对字节数组字符串进行Base64解码并生成图片
        if (StringUtils.isNotBlank(imgStr)) //图像数据为空
        {
            BASE64Decoder decoder = new BASE64Decoder();
            try
            {
                //Base64解码
                byte[] b = decoder.decodeBuffer(imgStr);
                for(int i=0;i<b.length;++i)
                {
                    if(b[i]<0)
                    {//调整异常数据
                        b[i]+=256;
                    }
                }
                //生成jpeg图片
//	            String imgFilePath = "D:\\tupian\\new.jpg";//新生成的图片
                OutputStream out = new FileOutputStream(imgFilePath);
                out.write(b);
                out.flush();
                out.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            res = true;
        }
        return res;
    }
}

