package com.brilliance.test;

import com.brilliance.domain.RequestTextMessage;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class MainTest {
	public static void main(String[] args) {
		String str = "<xml><StuName>1234567890123456</StuName></xml>";
		XStream xstream = new XStream(new DomDriver());
		xstream.processAnnotations(RequestTextMessage.class);
		RequestTextMessage re = (RequestTextMessage) xstream.fromXML(str);
	}
}
