package com.brilliance.util;

/**
 * @author google 常量类
 */
public class FinalConstantUtil {

	public static class Public {

		public static final String APPID = "wxad7fb06d082f15c5";

		public static final String APPSECRET = "d4624c36b6795d1d99dcf0547af5443d";
	}

	public static class Event {

		// 事件
		public static final String EVENT = "event";

		public static final String CLICK = "CLICK";

		// 文本
		public static final String TEXT = "text";

		// 绑定
		public static final String SUBSCRIBE = "subscribe";

		// 解除绑定
		public static final String UNSUBSCRIBE = "unsubscribe";
	}

	// 餐单URL
	public static class Menu {
		public static final String MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";
	}

	// TokenURL
	public static class Token {
		public static final String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	}

	// 模板消息URL
	public static class Message {
		public static final String TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";
	}

}
