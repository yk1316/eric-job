package net.coding.samples.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.cookie.Cookie;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import net.coding.samples.util.crifanLib;

@Component("baiduSignTask")
public class BaiduSignTask {

	private static Logger logger = LoggerFactory.getLogger(BaiduSignTask.class);
	
	private static crifanLib crl = new crifanLib();

	@Scheduled(cron = "0 * * * * ?")
	public void execute(){
		String baiduid = this.step_1_GetCookie_BAIDUID();
		String token = this.step_2_GetCookie_TOKEN();
		this.step_3_Login(token);
		this.step_4_Sign();
	}
	
	private String step_1_GetCookie_BAIDUID(){
		logger.info("====== 步骤1：获得BAIDUID的Cookie ======");

		String strBaiduUrl = "http://www.baidu.com/";
		HttpResponse baiduResp = crl.getUrlResponse(strBaiduUrl);

		List<Cookie> curCookieList = crl.getCurCookieStore().getCookies();
		crl.dbgPrintCookies(curCookieList, strBaiduUrl);
		for (Cookie ck : curCookieList) {
			String cookieName = ck.getName();
			if (cookieName.equals("BAIDUID")) {
				logger.info("正确：已找到cookie BAIDUID, " + ck.getValue());
				return ck.getValue();
			}
		}
		throw new RuntimeException("错误：没有找到cookie BAIDUID");
	}
	
	private String step_2_GetCookie_TOKEN(){
		logger.info("====== 步骤2：提取login_token ======");

		// https://passport.baidu.com/v2/api/?getapi&class=login&tpl=mn&tangram=true
		String getapiUrl = "https://passport.baidu.com/v2/api/?getapi&class=login&tpl=mn&tangram=true";
		String getApiRespHtml = crl.getUrlRespHtml(getapiUrl);

		List<Cookie> curCookieList = crl.getCurCookieStore().getCookies();
		crl.dbgPrintCookies(curCookieList, getapiUrl);

		// bdPass.api.params.login_token='3cf421493884e0fe9080593d05f4744f';
		Pattern tokenValP = Pattern.compile("bdPass\\.api\\.params\\.login_token='(?<tokenVal>\\w+)';");
		Matcher tokenValMatcher = tokenValP.matcher(getApiRespHtml);
		boolean foundTokenValue = tokenValMatcher.find();
		if (foundTokenValue) {
			String strTokenValue = tokenValMatcher.group("tokenVal"); // 3cf421493884e0fe9080593d05f4744f
			logger.info("正确：找到 bdPass.api.params.login_token=" + strTokenValue);
			return strTokenValue;
		} else {
			throw new RuntimeException("错误：没找到token");
		}
	}
	
	private void step_3_Login(String token){
		logger.info("======步骤3：登陆百度并检验返回的Cookie ======");

		String staticPageUrl = "http://www.baidu.com/cache/user/html/jump.html";

		List<NameValuePair> postDict = new ArrayList<NameValuePair>();
		postDict.add(new BasicNameValuePair("charset", "utf-8"));
		postDict.add(new BasicNameValuePair("token", token));
		postDict.add(new BasicNameValuePair("isPhone", "false"));
		postDict.add(new BasicNameValuePair("index", "0"));
		postDict.add(new BasicNameValuePair("staticpage", staticPageUrl));
		postDict.add(new BasicNameValuePair("loginType", "1"));
		postDict.add(new BasicNameValuePair("tpl", "mn"));
		postDict.add(new BasicNameValuePair("callback", "parent.bdPass.api.login._postCallback"));

		String strBaiduUsername = System.getProperty("baidu.username");
		String strBaiduPassword = System.getProperty("baidu.password");

		postDict.add(new BasicNameValuePair("username", strBaiduUsername));
		postDict.add(new BasicNameValuePair("password", strBaiduPassword));

		postDict.add(new BasicNameValuePair("verifycode", ""));
		postDict.add(new BasicNameValuePair("mem_pass", "on"));

		String baiduMainLoginUrl = "https://passport.baidu.com/v2/api/?login";
		String loginBaiduRespHtml = crl.getUrlRespHtml(baiduMainLoginUrl, null, postDict);

		HashMap<Object, Boolean> cookieNameDict = new HashMap<Object, Boolean>();
		cookieNameDict.put("BDUSS", false);
		cookieNameDict.put("PTOKEN", false);
		cookieNameDict.put("STOKEN", false);

		List<Cookie> curCookieList = crl.getCurCookieList();
		for (Object objCookieName : cookieNameDict.keySet().toArray()) {
			String strCookieName = objCookieName.toString();
			for (Cookie ck : curCookieList) {
				if (strCookieName.equalsIgnoreCase(ck.getName())) {
					cookieNameDict.put(strCookieName, true);
				}
			}
		}

		boolean bAllCookiesFound = true;
		for (Object objFoundCurCookie : cookieNameDict.values()) {
			bAllCookiesFound = bAllCookiesFound && Boolean.parseBoolean(objFoundCurCookie.toString());
		}

		if (bAllCookiesFound) {
			logger.info("成功模拟登陆百度首页！");
		} else {
			throw new RuntimeException("错误：模拟登陆百度首页 失败, " + loginBaiduRespHtml);
		}
	
	}
	
	private void step_4_Sign(){
		logger.info("======步骤4：百度签到 ======");
		String strBaiduUrl = "http://wenku.baidu.com/task/submit/signin";
		String respHtml = crl.getUrlRespHtml(strBaiduUrl);
		logger.info(respHtml);
	}
	
}
