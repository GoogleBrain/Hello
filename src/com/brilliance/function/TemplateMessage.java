package com.brilliance.function;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.brilliance.util.AccessTokenUtil;
import com.brilliance.util.DateUtil;
import com.brilliance.util.FinalConstantUtil;
import com.brilliance.util.HttpUtil;

import net.sf.json.JSONObject;

public class TemplateMessage extends QuartzJobBean {
	// 模板通知,定时发送时间提醒.
	private static Log log = LogFactory.getLog(TemplateMessage.class);

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		try {
			postMethod();
			//postWhether();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void postWhether() throws Exception {
		String url = FinalConstantUtil.Message.TEMPLATE_URL + AccessTokenUtil.getTokenInstance().getToken();

		Map<String, String> map = new HashMap<>();
		map.put("apikey", FinalConstantUtil.Public.APIKEY);
		JSONObject jo = JSONObject
				.fromObject(HttpUtil.httpRequest(FinalConstantUtil.Whether.WEHTHER_URL, "GET", null, map).toString());
		System.out.println(jo);
		JSONObject jo1 = (JSONObject) jo.get("retData");

		Map<String, String> map1 = new HashMap<>();
		map1.put("value", DateUtil.nowTimeToString());

		Map<String, String> map2 = new HashMap<>();
		map2.put("value", jo1.getString("weather"));

		Map<String, String> map3 = new HashMap<>();
		map3.put("value", jo1.getString("temp"));

		Map<String, String> map4 = new HashMap<>();
		map4.put("value", jo1.getString("WS"));

		Map<String, String> map5 = new HashMap<>();
		map5.put("value", jo1.getString("longitude"));

		Map<String, String> map6 = new HashMap<>();
		map6.put("value", jo1.getString("latitude"));

		Map<String, Object> map11 = new HashMap<>();
		map11.put("nowdate", map1);
		map11.put("whe", map2);
		map11.put("temp", map3);
		map11.put("wind", map4);
		map11.put("longitude", map5);
		map11.put("latitude", map6);

		Map<String, Object> map7 = new HashMap<>();
		map7.put("data", map11);
		map7.put("topcolor", "#FF0000");
		map7.put("url", "http://weixin.qq.com/download");
		map7.put("touser", "oH7eiv3w6cOVOzvHHK-sxdDe2tHg");
		map7.put("template_id", "S2CInIg9b4H-1Z-Qmy-j5slx5vz0vgLqP3GjngQLRfs");
		JSONObject ja = JSONObject.fromObject(map7);

		log.info("Time templte>>>>" + ja.toString());

		JSONObject jsonObject = HttpUtil.httpRequest(url, "POST", ja.toString(), null);

		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				int result = jsonObject.getInt("errcode");
				log.info("Time templte send status>>>>" + result);
			}
		}
	}

	public void postMethod() throws Exception {
		String url = FinalConstantUtil.Message.TEMPLATE_URL + AccessTokenUtil.getTokenInstance().getToken();

		Map<String, String> map1 = new HashMap<>();
		map1.put("value", DateUtil.nowTimeToString());
		map1.put("color", "#173177");

		Map<String, Object> map2 = new HashMap<>();
		map2.put("nowdate", map1);

		Map<String, Object> map7 = new HashMap<>();
		map7.put("data", map2);
		map7.put("topcolor", "#FF0000");
		map7.put("url", "http://weixin.qq.com/download");
		map7.put("touser", "oH7eiv3w6cOVOzvHHK-sxdDe2tHg");
		map7.put("template_id", "BHPRneyovpKQ54vZmsKPVO_0_jpf9ddvwyzvorUDE34");
		JSONObject ja = JSONObject.fromObject(map7);

		log.info("Time templte>>>>" + ja.toString());

		JSONObject jsonObject = HttpUtil.httpRequest(url, "POST", ja.toString(), null);

		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				int result = jsonObject.getInt("errcode");
				log.info("Time templte send status>>>>" + result);
			}
		}
	}
}