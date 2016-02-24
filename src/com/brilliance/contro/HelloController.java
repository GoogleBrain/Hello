package com.brilliance.contro;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.brilliance.domain.Image;
import com.brilliance.domain.Item;
import com.brilliance.domain.RequestTextMessage;
import com.brilliance.util.FinalConstantUtil;
import com.brilliance.util.XmlAndObj;

@Controller
public class HelloController {
	private static Log log = LogFactory.getLog(HelloController.class);

	/*
	 * 对URL合法性记性校验.
	 */
	@ResponseBody
	@RequestMapping(value = "/", method = { RequestMethod.GET })
	public String index(@RequestParam("signature") String signature, @RequestParam("timestamp") Long timestamp,
			@RequestParam("nonce") Long nonce, @RequestParam("echostr") String echostr) {
		return echostr;
	}

	/*
	 * 接收微信端发送的消息,对不同的消息作出不同的回应.
	 */
	@RequestMapping(value = "/", method = { RequestMethod.POST })
	public void index(HttpServletRequest request, HttpServletResponse response) {
		InputStream inputStream;
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			inputStream = request.getInputStream();
			String postData = IOUtils.toString(inputStream);
			log.info("receive message>>>>>" + postData);
			RequestTextMessage msg = (RequestTextMessage) XmlAndObj.XmlToObject(RequestTextMessage.class, postData);
			log.info("xml 2 obj >>>>>" + msg.toString());
			String returnXml = getReplyTextMessage(msg, msg.getFromUserName(), msg.getToUserName());
			if (FinalConstantUtil.Event.EVENT.equals(msg.getMsgType())
					&& FinalConstantUtil.Event.UNSUBSCRIBE.equals(msg.getEvent())) {
				log.info(msg.getFromUserName() + ">>>> exit");
			} else {
				response.getWriter().write(returnXml);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 回复消息
	private String getReplyTextMessage(RequestTextMessage msg, String fromUserName, String toUserName) {
		RequestTextMessage reMess = new RequestTextMessage();
		reMess.setToUserName("<![CDATA[" + fromUserName + "]]>");
		reMess.setFromUserName("<![CDATA[" + toUserName + "]]>");
		reMess.setCreateTime(new Long(new Date().getTime()).toString());
		String mt = msg.getMsgType();
		String event = msg.getEvent();
		String content = msg.getContent().trim();
		boolean flag = true;
		if (FinalConstantUtil.Event.EVENT.equals(mt) && FinalConstantUtil.Event.SUBSCRIBE.equals(event)) {
			// 关注事件
			reMess.setMsgType("<![CDATA[text]]>");
			reMess.setContent("<![CDATA[欢迎使用GoogleBrain微信公众号,回复\n1:文本消息;\n2:图片消息;\n3:图文消息;\n]]>");
			flag = false;
		} else if (FinalConstantUtil.Event.TEXT.equals(mt) && "1".equals(content)) {
			// 回复文本
			reMess.setMsgType("<![CDATA[text]]>");
			reMess.setContent("<![CDATA[输入的消息为:" + content + "]]>");
			flag = false;
		} else if (FinalConstantUtil.Event.TEXT.equals(mt) && "2".equals(content)) {
			// 回复图片
			reMess.setMsgType("<![CDATA[image]]>");
			Image image = new Image();
			image.setMediaId("<![CDATA[f29v8KUftcQUkIamap1KR-dmA4ACqe00eDrMhM2cWxa40UeuAnZH-nemOyZ_WSeu]]>");
			reMess.setImage(image);
			flag = false;
		} else if (FinalConstantUtil.Event.TEXT.equals(mt) && "3".equals(content)) {
			// 回复图文
			reMess.setMsgType("<![CDATA[news]]>");
			reMess.setArticleCount(3);
			List<Item> items = new ArrayList<>();
			Item item = new Item();
			item.setTitle("<![CDATA[新闻]]>");
			item.setPicUrl("<![CDATA[http://img04.bibimai.com/product_big/25/58/35/5255835.jpg]]>");
			item.setDescription("<![CDATA[新闻]]>");
			item.setUrl("<![CDATA[]]>");
			items.add(item);

			Item item2 = new Item();
			item2.setTitle("<![CDATA[网易]]>");
			item2.setPicUrl("<![CDATA[http://img04.bibimai.com/product_big/25/58/35/5255835.jpg]]>");
			item2.setDescription("<![CDATA[网易]]>");
			item2.setUrl("<![CDATA[http://www.163.com]]>");
			items.add(item2);

			Item item3 = new Item();
			item3.setTitle("<![CDATA[凤凰]]>");
			item3.setPicUrl("<![CDATA[http://img04.bibimai.com/product_big/25/58/35/5255835.jpg]]>");
			item3.setDescription("<![CDATA[凤凰网]]>");
			item3.setUrl("<![CDATA[http://www.ifeng.com]]>");
			items.add(item3);
			reMess.setArticles(items);
		}
		reMess.setFuncFlag("<![CDATA[0]]>");
		String returnMessage=XmlAndObj.ObjectToXml(reMess, flag, "Articles").replaceAll("&lt;", "<").replaceAll("&gt;", ">");
		log.info("return 2 weixin client >>>>>" + returnMessage);
		return returnMessage;
	}

	public static void main(String[] args) {
		RequestTextMessage re = new RequestTextMessage();
		re.setMsgType("text");
		re.setEvent("text");
		re.setContent("3");
		System.out.println(new HelloController().getReplyTextMessage(re, "To", "From"));
	}
}
