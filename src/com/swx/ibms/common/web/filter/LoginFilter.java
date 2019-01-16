package com.swx.ibms.common.web.filter;


import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.rbac.bean.RYBM;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

/**
 * 判断用户是否登录，如果没有登录返回到登录页面
 *
 * @author 魏明欣
 *
 * @since 2017年3月3日
 */
@WebFilter({ "*.html", "/service/*" })
public class LoginFilter implements Filter {
	/**
	 * 日志对象
	 */
	private Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

	/**
	 * Default constructor.
	 */
	public LoginFilter() {
	}


	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	/**
	 * @param request
	 *            请求参数
	 * @param response
	 *            响应
	 * @param chain
	 *            链
	 * @exception IOException
	 *                IO异常
	 * @exception ServletException
	 *                severlet异常
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
//		LicenseVertify vlicense = new LicenseVertify("noryar"); // 项目唯一识别码，对应生成配置文件的subject
//		String path = request.getServletContext().getRealPath("/WEB-INF/classes");
//		try {
//			vlicense.install(path);
//		} catch (Exception e) {
//			RYBM rybm = Constant.CURRENT_USER.get();
//			LogService logService = new LogServiceImpl();
//			logService.error(rybm.getDwbm(), rybm.getGh(), rybm.getMc(), Constant.RZLX_CWRZ,e.getMessage());
//			return;
//		}
//		int security = vlicense.vertify();
////		if (security == 0) {
////			logger.warn("验证证书成功!");
////		} else
//		if (security != 0) {
//			logger.warn("证书已经过期!");
//			return;
//		}
//		else {
//			logger.warn("验证证书失败!");
//			return;
//		}

		HttpServletRequest hRequest = (HttpServletRequest) request;
		String uriLogin = hRequest.getContextPath() + "/login.html";// 登录页面

		HttpSession session = hRequest.getSession();
		Object userObj = session.getAttribute("ry");
		String uri = hRequest.getRequestURI();
		if(StringUtils.contains(uri, "zhyz")){
			chain.doFilter(request, response);
		}
		if (Objects.isNull(userObj) && !uriLogin.equals(uri) && !StringUtils.contains(uri, "tree")
				&& !StringUtils.contains(uri, "login")) {

			HttpServletResponse hResponse = (HttpServletResponse) response;
			if (StringUtils.endsWith(uri, ".html")) { // 普通html页面请求
				if (StringUtils.endsWith(uri, "toindex.html")) {
					String key = request.getParameter("key");
					if (StringUtils.isEmpty(key)) {
						hResponse.getWriter().println("<script>top.location.href ='login.html'</script>");
					} else {
						chain.doFilter(request, hResponse);
					}
				} else {
					hResponse.getWriter().println("<script>top.location.href ='login.html'</script>");
				}
			} else if (StringUtils.endsWith(uri, "/")) {
				hResponse.getWriter().println("<script>top.location.href ='index.html'</script>");
			} else { // AJAX请求判断，由前台jquery跳转，参看jquery.min.js重写的ajax方法。
				hResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			}
		} else {
			Constant.CURRENT_USER.set((RYBM) userObj);
			chain.doFilter(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	/**
	 * @param fConfig
	 *            布吉岛
	 * @exception ServletException
	 *                severlet异常
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
