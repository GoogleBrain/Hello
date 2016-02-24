package com.brilliance.function;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.brilliance.util.AccessTokenUtil;
import com.brilliance.util.FinalConstantUtil;
import com.brilliance.util.HttpUtil;

import net.sf.json.JSONObject;

public class MenuFunction extends HttpServlet {
	/**
	 * 创建自定义菜单
	 */
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(MenuFunction.class);

	@Override
	public void init() throws ServletException {
		try {
			postMethod();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	public void postMethod() throws Exception {
		Map<String, String> map1 = new HashMap<>();
		map1.put("type", "click");
		map1.put("name", "查询");
		map1.put("key", "select");

		Map<String, String> map2 = new HashMap<>();
		map2.put("type", "view");
		map2.put("name", "新闻");
		map2.put("url", "http://www.ifeng.com");

		List<Map> lists = new ArrayList<>();
		lists.add(map1);
		lists.add(map2);

		Map<String, Object> map0_1 = new HashMap<>();
		map0_1.put("name", "功能菜单");
		map0_1.put("sub_button", lists);

		Map<String, String> map8 = new HashMap<>();
		map8.put("type", "click");
		map8.put("name", "助手小妹");
		map8.put("key", "help");

		List<Map> lists0_2 = new ArrayList<>();
		lists0_2.add(map8);

		Map<String, Object> map0_2 = new HashMap<>();
		map0_2.put("name", "帮助");
		map0_2.put("sub_button", lists0_2);

		List<Object> lists2 = new ArrayList<>();
		lists2.add(map0_1);
		lists2.add(map0_2);

		Map<String, Object> map9 = new HashMap<>();
		map9.put("button", lists2);

		JSONObject ja = JSONObject.fromObject(map9);

		log.info("menu Info >>>>" + ja.toString());

		JSONObject jsonObject = HttpUtil.httpRequest(
				FinalConstantUtil.Menu.MENU_URL + AccessTokenUtil.getTokenInstance().getToken(), "POST", ja.toString());
		if (null != jsonObject) {
			log.info("create menu >>>>" + jsonObject);
		}
	}
}
