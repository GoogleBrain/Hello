package com.brilliance.util;

import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.brilliance.domain.AccessToken;
import net.sf.json.JSONObject;

/*
 * 系统启动立即加载一次.
 */
public class AccessTokenUtil extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public static AccessToken accessToken = null;
	private static Log log = LogFactory.getLog(AccessTokenUtil.class);

	@Override
	public void init() throws ServletException {
		newAccessTokenInstance();
	}

	public void newAccessTokenInstance() {
		log.info("Create AccessToken start>>>>>");
		String requestUrl = FinalConstantUtil.Token.TOKEN_URL.trim().replace("APPID", FinalConstantUtil.Public.APPID)
				.replace("APPSECRET", FinalConstantUtil.Public.APPSECRET);
		JSONObject jsonObject = HttpUtil.httpRequest(requestUrl, "GET", null, null);
		if (null != jsonObject) {
			try {
				accessToken = new AccessToken();
				accessToken.setToken(jsonObject.getString("access_token"));
				accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
				accessToken.setCreateTime(new Date().getTime());
			} catch (Exception e) {
				accessToken = null;
			}
		}
		log.info("Create AccessToken end>>>>>" + accessToken);
	}

	public static AccessToken getTokenInstance() {
		if (accessToken != null && new Date().getTime() > accessToken.getCreateTime() + 110 * 60 * 1000) {
			synchronized (AccessTokenUtil.class) {
				new AccessTokenUtil().newAccessTokenInstance();
			}
		}
		log.info("Get New AccessToken end>>>>>" + accessToken);
		return accessToken;
	}
}
