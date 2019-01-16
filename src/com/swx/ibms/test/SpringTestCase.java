package com.swx.ibms.test;


import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration/*(value = "../WebContent")*/
//指定bean注入的配置文件
//@ContextConfiguration(locations = { "classpath*:applicationContext.xml", "classpath*:mvc-servlet.xml"})
@ContextHierarchy({
        @ContextConfiguration(name = "parent", locations = "classpath:baseConfig/applicationContext.xml"),
//        @ContextConfiguration(name = "child", locations = "classpath:mvc-servlet.xml")
})

//使用标准的JUnit @RunWith注释来告诉JUnit使用Spring TestRunner

public class SpringTestCase extends AbstractJUnit4SpringContextTests {
//    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Autowired
    protected WebApplicationContext wbApplicationContext;

    protected MockMvc mockMvc;

    // 模拟request,response
    protected MockHttpServletRequest request;
    protected MockHttpServletResponse response;

    @Before
    public void setup() throws Exception {

        this.mockMvc = webAppContextSetup(this.wbApplicationContext).build();

        request = new MockHttpServletRequest();
        request.setCharacterEncoding("UTF-8");
        response = new MockHttpServletResponse();

    }
}