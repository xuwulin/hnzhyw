package com.swx.ibms.business.rbac.controller;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonObject;
import com.swx.ibms.business.archives.service.JcgSfdaCxService;
import com.swx.ibms.business.common.bean.Message;
import com.swx.ibms.business.common.service.LogService;
import com.swx.ibms.business.common.utils.Constant;
import com.swx.ibms.business.common.utils.XxptMessage;
import com.swx.ibms.business.performance.service.HCPZService;
import com.swx.ibms.business.rbac.bean.JSBM;
import com.swx.ibms.business.rbac.bean.RYBM;
import com.swx.ibms.business.rbac.service.LoginService;
import com.swx.ibms.common.utils.FileUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 登录控制器
 * 
 * @author 李治鑫
 *
 */
@SuppressWarnings("all")
@RequestMapping("/login")
@Controller
public class LoginController {

	/**
	 * 日志对象
	 */
	private static final Logger LOG = Logger.getLogger(LoginController.class);

	/**
	 * 系统管理服务接口
	 */
	@Resource
	private LoginService loginService;
	/**
	 * 核查配置服务接口
	 */
	@Resource
	private HCPZService hcpzservice;
	/**
	 * 司法档案查询
	 */
	@Resource
	private JcgSfdaCxService jcgSfdaCxService;

	/**
	 * 日志记录服务
	 */
	@Resource
	private LogService logService;

	/**
	 * 普通登陆
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	/**
	 * 普通登录方式
	 * 
	 * @param request
	 *            请求参数
	 * @param response
	 *            响应
	 * @return 登录信息
	 * @throws Exception
	 *             异常
	 */
	@RequestMapping(value = "/pt", method = RequestMethod.POST)
	@ResponseBody 
	protected String login(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		String dwmc = request.getParameter("dwmc");// 单位名称
		String dwbm = request.getParameter("dwbm");// 单位编码
		String yhm = request.getParameter("yhm");// 用户名
		String kl = request.getParameter("kl");// 口令
		String type = request.getParameter("type");// 1：手动登录，2.自动登录
		try {
			dwmc = URLDecoder.decode(dwmc, "utf-8");
		} catch (Exception e) {
		}
//		response.setHeader("Access-Control-Allow-Origin", "*"); //允许的域名（ * 所有域）
//		response.setHeader("Access-Control-Allow-Methods", "GET, POST, WEBSOCKET"); //允许的方法
//		response.setHeader("Access-Control-Allow-Headers", "upload, X-Requested-With, Content-Type");
		// 存cookie
		Cookie dwmcCookie = new Cookie("dwmc", URLEncoder.encode(dwmc, "utf-8"));
		// 设置cookie的时间为永久
		dwmcCookie.setMaxAge(Integer.MAX_VALUE);
		Cookie dwbmCookie = new Cookie("dwbm", URLEncoder.encode(dwbm, "utf-8"));
		dwbmCookie.setMaxAge(Integer.MAX_VALUE);
		Cookie yhmCookie = new Cookie("yhm", URLEncoder.encode(yhm, "utf-8"));
		yhmCookie.setMaxAge(Integer.MAX_VALUE);

		response.addCookie(dwmcCookie);
		response.addCookie(dwbmCookie);
		response.addCookie(yhmCookie);

		// int num=kl.length();
		if ("2".equals(type)) {
			if (kl.length() != Constant.NUM_32) {
				// 将kl密码转换为MD5格式
				kl = DigestUtils.md5DigestAsHex(kl.getBytes());
			}
		} else {
			// 将kl密码转换为MD5格式
			kl = DigestUtils.md5DigestAsHex(kl.getBytes());
		}
		List<RYBM> ry = null;
		if (StringUtils.isNotBlank(dwbm) && StringUtils.isNotBlank(yhm)) {
			try {
				ry = loginService.getRyByGzzh(dwbm, yhm);
			} catch (Exception e) {
				LOG.error(StringUtils.EMPTY, e);
			}
		}

		Map<String, String> returnMap = new HashMap<>();

		if (CollectionUtils.isEmpty(ry)) {
			returnMap.put("status", "0");
			returnMap.put("msg", "用户名或密码不正确！");
			return Constant.GSON.toJson(returnMap);
		}

		String dbkl = ry.get(0).getKl();

		if (!Objects.isNull(dbkl) && dbkl.equals(kl)) {// 验证成功
			List<JSBM> bmjs1 = null;
			List<String> bmjs = new ArrayList<String>();// 部门角色
			List<Integer> ryjs = new ArrayList<Integer>();// 人员角色
			List<String> bmysnum = new ArrayList<String>(); // 部门映射
			try {
				bmjs1 = loginService.getgetBmJs(dwbm, ry.get(0).getGh());
				int fgcount = (int)jcgSfdaCxService.cxFgBm(dwbm,
						ry.get(0).getGh()).get("count");//查询是否有分管部门以确定是否是分管领导
				if (fgcount>0) {
					ryjs.add(Constant.NUM_2);// 分管领导
				}
				for (int i = 0; i < bmjs1.size(); i++) {
					bmjs.add(((JSBM) bmjs1.get(i)).getBmjs());
					String jsbm = ((JSBM) bmjs1.get(i)).getJsbm();
					String jsmc = ((JSBM) bmjs1.get(i)).getJsmc();
					String bmys = ((JSBM) bmjs1.get(i)).getBmys();
					bmysnum.add(bmys);
					if (bmys == null || bmys.equals("")) {
						if (StringUtils.contains(jsmc, "长")
								||StringUtils.contains(jsmc, "巡视员")
								||StringUtils.contains(jsmc, "委员")
								||StringUtils.containsOnly(jsmc, "主任")
								||StringUtils.containsOnly(jsmc, "副主任")) {
							ryjs.add(Constant.NUM_1);// 领导
						}else{
							ryjs.add(Constant.NUM_5);// 普通人
						}
					} else if (bmys.contains("0102")) {
						ryjs.add(Constant.NUM_8);// 考评委员会
					} else if (bmys.contains("9100")) {
						ryjs.add(0);// 人事部
					} else if (bmys.contains("0101")) {
						ryjs.add(Constant.NUM_6);// 纪检
					} else if (bmys.contains("1100")) {
						ryjs.add(Constant.NUM_7);// 案管
					} else if (bmys.contains("0000")) {
						/*if (jsbm.equals("001")) {
							ryjs.add(Constant.NUM_1);// 检察长
						} else {
							ryjs.add(Constant.NUM_4);// 承办人
						}*/
						if (StringUtils.contains(jsmc, "长")) {
							ryjs.add(Constant.NUM_1);// 检察长
						}else{
							ryjs.add(Constant.NUM_4);// 承办人
						}
					} else {
						/*if (jsbm.equals("001") || jsbm.equals("002")) {
							ryjs.add(Constant.NUM_3);// 部门领导
						} else {
							ryjs.add(Constant.NUM_4);// 承办人
						}*/
						if (StringUtils.contains(jsmc, "长")
								||StringUtils.containsOnly(jsmc, "主任")
								||StringUtils.containsOnly(jsmc, "副主任")
								||StringUtils.contains(jsmc, "负责人")
								||StringUtils.contains(jsmc, "委员")) {
							ryjs.add(Constant.NUM_3);// 部门领导
						}else{
							ryjs.add(Constant.NUM_4);// 承办人
						}
					}
				}
				Collections.sort(ryjs);// 排序
			} catch (Exception e) {
				LOG.error(StringUtils.EMPTY, e);
			}

			HttpSession session = request.getSession();
			session.setAttribute("ry", ry.get(0));
			session.setAttribute("bmjs", bmjs);
			session.setAttribute("ryjs", ryjs);
			session.setAttribute("bmys", bmysnum);

			// 记录日志
			logService.info(ry.get(0).getDwbm(), ry.get(0).getGh(),
					ry.get(0).getMc(), Constant.RZLX_CZRZ, "登录系统");

			// Constant.currentUser.set(ry.get(0));
			returnMap.put("status", "1");
		} else {
			// 验证失败
			returnMap.put("status", "0");
			returnMap.put("msg", "用户名或密码不正确！");
		}

		return Constant.GSON.toJson(returnMap);
	}

	/**
	 * 注销登录
	 * 
	 * @param request
	 *            请求参数
	 * @return 注销信息
	 * @throws Exception
	 *             异常
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	@ResponseBody
	protected String logOut(HttpServletRequest request) throws Exception {

		HttpSession session = request.getSession();

		session.removeAttribute("ry");

		return StringUtils.EMPTY;
	}

	/**
	 * 获取cookies里存的内容
	 * 
	 * @param request
	 *            请求参数
	 * @return cookie信息
	 * @throws Exception
	 *             异常
	 */
	@RequestMapping(value = "/getlocookie", method = RequestMethod.POST)
	@ResponseBody
	protected String loginCookie(HttpServletRequest request) throws Exception {
		Map<String, String> resultMap = new HashMap<>();
		String dwmc = "";
		String dwbm = "";
		String yhm = "";
		// 获取当前攒点的所有Cookie
		Cookie[] cookies = request.getCookies();
		try {
			for (int i = 0; i < cookies.length; i++) {
				if ((cookies[i].getName()).equals("dwmc")) {
					dwmc = cookies[i].getValue();
					resultMap.put("dwmc", URLDecoder.decode(dwmc, "utf-8"));
				} else if ((cookies[i].getName()).equals("dwbm")) {
					dwbm = cookies[i].getValue();
					resultMap.put("dwbm", URLDecoder.decode(dwbm, "utf-8"));
				} else if ((cookies[i].getName()).equals("yhm")) {
					yhm = cookies[i].getValue();
					resultMap.put("yhm", URLDecoder.decode(yhm, "utf-8"));
				}
			}
		} catch (Exception e) {
		}

		return Constant.GSON.toJson(resultMap);
	}

	/**
	 * 自动登录
	 * 
	 * @param request
	 *            请求参数
	 * @return 登录信息
	 * @throws Exception
	 *             异常
	 */
	@RequestMapping(value = "/zddl", method = RequestMethod.POST)
	@ResponseBody
	protected String zddl(HttpServletRequest request) throws Exception {
		String key = request.getParameter("key");
		byte[] b = null;
		if (key != null) {
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				b = decoder.decodeBuffer(key);
				key = new String(b, "utf-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		JsonObject resultjson = (JsonObject) Constant.JSON_PARSER.parse(key);

		String dwmc = null;
		String dwbm = resultjson.get("dwbm").getAsString();// 单位编码
		String yhm = resultjson.get("dlbm").getAsString();// 用户名
		List<RYBM> ry = null;
		String dbkl = null;
		Map<String, Object> map = new HashMap<>();
		if (StringUtils.isNotBlank(dwbm) && StringUtils.isNotBlank(yhm)) {
			try {
				ry = loginService.getRyByGzzh(dwbm, yhm);
				dbkl = ry.get(0).getKl();
				dwmc = (String) (hcpzservice.getdwjb(dwbm)).get("p_dwmc");
				map.put("dwbm", dwbm);
				map.put("yhm", yhm);
				map.put("kl", dbkl);
				map.put("dwmc", dwmc);

				// 记录日志
				logService.info(ry.get(0).getDwbm(), ry.get(0).getGh(), 
						ry.get(0).getMc(), Constant.RZLX_CZRZ, "登录系统");

				Constant.CURRENT_USER.set(ry.get(0));

				return Constant.GSON.toJson(map);
			} catch (Exception e) {
				LOG.error(StringUtils.EMPTY, e);
			}
		}
		return Constant.GSON.toJson(map);
	}

	/**
	 * 根据登录别名和单位编码查询登录人的工号，再结合单位编码查询管理员表，该登录人是不是超级管理员
	 * 
	 * @param req
	 *            请求对象
	 * @return msg="Y" 或者msg="N"
	 */
	@RequestMapping(value = "/isOrNotAdmin", method = RequestMethod.POST)
	@ResponseBody
	public String isOrNotAdmin(HttpServletRequest req) {
		Map<String, Object> map = new HashMap<>();
		String dwbm = req.getParameter("dwbm");
		String dlbm = req.getParameter("dlbm");
		if (StringUtils.isNotEmpty(dwbm) && StringUtils.isNotEmpty(dlbm)) {
			String msg = loginService.isOrNotAdmin(dwbm, dlbm);
			map.put("msg", msg);
		}
		return Constant.GSON.toJson(map);
	}

	/**
	 * 用户验证
	 * 验证账号密码，完成绑定（消息平台）
	 *
	 * @param req
	 *
	 * @return
	 */
	@RequestMapping(value = "/yhyz", method = RequestMethod.POST)
	@ResponseBody
	public String yhyz(HttpServletRequest req) throws IOException {
		Map<String, Object> returnMap = new HashMap<>();
		//单位编码
		String dwbm = req.getParameter("dwbm").trim();
		//用户名
		String yhm = req.getParameter("yhm").trim();
		//口令
		String kl = req.getParameter("kl").trim();
		if("".equals(dwbm)){
			returnMap.put("status", "1");
			returnMap.put("msg", "请选择单位！");
			return Constant.GSON.toJson(returnMap);
		}
		if("".equals(yhm)){
			returnMap.put("status", "1");
			returnMap.put("msg", "请输入用户名！");
			return Constant.GSON.toJson(returnMap);
		}
		if("".equals(kl)){
			returnMap.put("status", "1");
			returnMap.put("msg", "请输入密码！");
			return Constant.GSON.toJson(returnMap);
		}
		//口令加密
		kl = DigestUtils.md5DigestAsHex(kl.getBytes());
		List<RYBM> ry = null;
			try {
				ry = loginService.getRyByGzzh(dwbm, yhm);
			} catch (Exception e) {
				LOG.error(StringUtils.EMPTY, e);
			}
		if(ry.size()==0){
			returnMap.put("status", 1);
			returnMap.put("msg", "用户名或密码错误");
			return Constant.GSON.toJson(returnMap);
		}
		//进行口令对比
		if(ry.get(0).getKl().equals(kl)){
			//单位编码+工号
			returnMap.put("status", 0);
			returnMap.put("data",dwbm+","+ry.get(0).getGh());
			return Constant.GSON.toJson(returnMap);
		}
		returnMap.put("status", 1);
		returnMap.put("msg", "用户名或密码错误");
		return Constant.GSON.toJson(returnMap);
	}

	/**
	 * 根据登录工号和单位编码查询登录人所有部门、所有角色 
	 * 
	 * @param req
	 *            请求对象
	 * @return 返回登录人信息
	 */
	@RequestMapping(value = "/getGrxxByDwbmAndGh", method = RequestMethod.POST)
	@ResponseBody
	public String getGrxxByDwbmAndGh(@RequestParam(value="dwbm",required=false)String dwbm,
									 @RequestParam(value="gh",required=false)String gh,
									 HttpServletRequest req) {
		List<String> resList = new ArrayList<String>();
		String paramDwbm = StringUtils.EMPTY;
		String paramGh = StringUtils.EMPTY;
		//如果页面没传工号和单位编码则取登录人的单位编码和工号去获取登录人的信息，eg：单位、部门等
		if(StringUtils.isBlank(dwbm)||StringUtils.isBlank(gh)){
			RYBM ry = (RYBM) req.getSession().getAttribute("ry");
			paramDwbm = ry.getDwbm();// 登录人单位编码
			paramGh = ry.getGh();// 登录人工号
		}else{
			paramDwbm = dwbm;
			paramGh = gh;
		}
		resList = loginService.getGrxxByDwbmAndGh(paramDwbm, paramGh);
		return Constant.GSON.toJson(resList);
	}

	/**
	 * 推送消息，生成消息平台需要的消息格式，并把地址也发送前台（消息平台）
	 * @param req
	 * @param state 状态
	 * @param read 是否已读
	 * @param title 标题
	 * @param summary 内容
	 * @param btsrbs 标识 ：用户的单位编码+工号
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/sendMassege", method = RequestMethod.POST)
	@ResponseBody
	public String sendMassege(HttpServletRequest req,String state,boolean read,String title,String summary,String btsrbs) throws IOException {
		//通过单位编码，工号获取用户名
		String yhm = loginService.getYhm(btsrbs.split("\\,")[0], btsrbs.split("\\,")[1]);
		//返回的map
		Map<String,Object> returnMap = new HashMap<>();
		//可发送多条message。目前只做了一条
		List<Message> messageList = new ArrayList<>();
		//创建Message
		Message message = new Message(  state, read, btsrbs,
				title, summary, new Date());
		//这个是需要使用的用户名，但是message没有这个字段，目前使用msgsourece暂时接受，前台会做处理
		message.setMsgsource(yhm);
		//message放入list
		messageList.add(message);

		//处理状态
		returnMap.put("errno", 0);

		//传给消息平台的数据格式
		Map<String,Object> map = new HashMap<>();
		map.put("message", messageList);
		map.put("sys_id", XxptMessage.getSys_id());

		//返回数据导前台
		returnMap.put("data",map);
		//消息平台的地址
		returnMap.put("xxptdz",XxptMessage.getXxpt_dz());
		return Constant.GSON.toJson(returnMap);
	}

	/**
	 *  获取消息平台地址和消息平台关于我们系统的ID（消息推送）
	 * @return 消息平台地址和海南系统平台ID
	 * @throws IOException
	 */
	@RequestMapping(value = "/getXxptdzAndsysID", method = RequestMethod.POST)
	@ResponseBody
	public String getXxptdzAndsysID() throws IOException {
		return XxptMessage.getSys_id()+","+XxptMessage.getXxpt_dz();
	}

	/**
	 * 获取文件，所有html页面，在view/ceshi.html使用
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/getFiles", method = RequestMethod.POST)
	@ResponseBody
	public String getFiles(HttpServletRequest req) {
		Map<String,Object> returnMap = new HashMap<>();
		returnMap.put("errno", 0);
		returnMap.put("data", FileUtil.getFiles(req));

		//装填参数
		return JSON.toJSONString(returnMap);
	}

	/**
	 *  页面跳转（消息平台）
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ymtz", method = RequestMethod.POST)
	@ResponseBody
	public String ymtz(HttpServletRequest request) {
		//标识：用户的单位编码和用户ID ，由逗号隔开
		String bs = request.getParameter("bs");
		//用户名
		String yhm = request.getParameter("yhm").trim();
		//判断标识内容是否正确
		if(bs==null||bs.indexOf(",")==-1||yhm==null){
			return JSON.toJSONString(false);
		}
		//单位编码
		String dwbm = bs.split("\\,")[0];
		//工号
		String gh = bs.split("\\,")[1];
		HttpSession session = request.getSession();
		//判断此人是否登录
		if(session!=null){
			RYBM rybm = (RYBM) session.getAttribute("ry");
			if(rybm!=null){
				if(dwbm.equals(rybm.getDwbm())&& gh.equals(rybm.getGh())){
					return JSON.toJSONString(true);//loginService.getGrxxByDwbmAndGh("")
				}
			}
		}
		//调用登录，进行登录验证
		List<RYBM> ryxx = loginService.getRyByGzzh(dwbm, yhm);
		if(ryxx.size()==0) {
			return JSON.toJSONString(false);
		}
				List<JSBM> bmjs1 = null;
				List<String> bmjs = new ArrayList<String>();// 部门角色
				List<Integer> ryjs = new ArrayList<Integer>();// 人员角色
				List<String> bmysnum = new ArrayList<String>(); // 部门映射
				try {
					bmjs1 = loginService.getgetBmJs(ryxx.get(0).getDwbm(), ryxx.get(0).getGh());
					int fgcount = (int)jcgSfdaCxService.cxFgBm(ryxx.get(0).getDwbm(),
							ryxx.get(0).getGh()).get("count");//查询是否有分管部门以确定是否是分管领导
					if (fgcount>0) {
						ryjs.add(Constant.NUM_2);// 分管领导
					}
					for (int i = 0; i < bmjs1.size(); i++) {
						bmjs.add(((JSBM) bmjs1.get(i)).getBmjs());
						String jsbm = ((JSBM) bmjs1.get(i)).getJsbm();
						String jsmc = ((JSBM) bmjs1.get(i)).getJsmc();
						String bmys = ((JSBM) bmjs1.get(i)).getBmys();
						bmysnum.add(bmys);
						if (bmys == null || bmys.equals("")) {
							if (StringUtils.contains(jsmc, "长")
									||StringUtils.contains(jsmc, "巡视员")
									||StringUtils.contains(jsmc, "委员")
									||StringUtils.containsOnly(jsmc, "主任")
									||StringUtils.containsOnly(jsmc, "副主任")) {
								ryjs.add(Constant.NUM_1);// 领导
							}else{
								ryjs.add(Constant.NUM_5);// 普通人
							}
						} else if (bmys.contains("0102")) {
							ryjs.add(Constant.NUM_8);// 考评委员会
						} else if (bmys.contains("9100")) {
							ryjs.add(0);// 人事部
						} else if (bmys.contains("0101")) {
							ryjs.add(Constant.NUM_6);// 纪检
						} else if (bmys.contains("1100")) {
							ryjs.add(Constant.NUM_7);// 案管
						} else if (bmys.contains("0000")) {
						/*if (jsbm.equals("001")) {
							ryjs.add(Constant.NUM_1);// 检察长
						} else {
							ryjs.add(Constant.NUM_4);// 承办人
						}*/
							if (StringUtils.contains(jsmc, "长")) {
								ryjs.add(Constant.NUM_1);// 检察长
							}else{
								ryjs.add(Constant.NUM_4);// 承办人
							}
						} else {
						/*if (jsbm.equals("001") || jsbm.equals("002")) {
							ryjs.add(Constant.NUM_3);// 部门领导
						} else {
							ryjs.add(Constant.NUM_4);// 承办人
						}*/
							if (StringUtils.contains(jsmc, "长")
									||StringUtils.containsOnly(jsmc, "主任")
									||StringUtils.containsOnly(jsmc, "副主任")
									||StringUtils.contains(jsmc, "负责人")
									||StringUtils.contains(jsmc, "委员")) {
								ryjs.add(Constant.NUM_3);// 部门领导
							}else{
								ryjs.add(Constant.NUM_4);// 承办人
							}
						}
					}
					Collections.sort(ryjs);// 排序
				} catch (Exception e) {
					LOG.error(StringUtils.EMPTY, e);
				}

				session.setAttribute("ry", ryxx.get(0));
				session.setAttribute("bmjs", bmjs);
				session.setAttribute("ryjs", ryjs);
				session.setAttribute("bmys", bmysnum);

				// 记录日志
				logService.info(ryxx.get(0).getDwbm(), ryxx.get(0).getGh(),
						ryxx.get(0).getMc(), Constant.RZLX_CZRZ, "登录系统");

		return JSON.toJSONString(true);
	}

	//获取自己系统的IP
	public String getRemortIP(HttpServletRequest request) {
		if (request.getHeader("x-forwarded-for") == null) {
			return request.getRemoteAddr();
		}
		return request.getHeader("x-forwarded-for");
	}
	//时间转换
	public String getStrTime(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}


	/**
	 *  获取用户名，用于消息推送
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getYhm", method = RequestMethod.POST)
	@ResponseBody
	public String getYhm(HttpServletRequest request) {
		String dwbm = request.getParameter("dwbm");
		String gh = request.getParameter("gh");
		return loginService.getYhm(dwbm, gh);
	}






}
