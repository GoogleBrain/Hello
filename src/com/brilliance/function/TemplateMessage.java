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
		} catch (Exception e) {
			e.printStackTrace();
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

		JSONObject jsonObject = HttpUtil.httpRequest(url, "POST", ja.toString());

		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				int result = jsonObject.getInt("errcode");
				log.info("Time templte send status>>>>" + result);
			}
		}
	}
}