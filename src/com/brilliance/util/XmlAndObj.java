package com.brilliance.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XmlAndObj {

	/**
	 * XML转成对象
	 * 
	 * @param cl
	 * @param str
	 * @return
	 */
	public static Object XmlToObject(Class cl, String str) {
		XStream xstream = new XStream(new DomDriver());
		xstream.processAnnotations(cl);
		return xstream.fromXML(str);
	}

	/**
	 * 对象转成XML
	 * 
	 * @param c1
	 * @param str
	 * @return
	 */
	public static String ObjectToXml(Object c1, boolean flag, String fieldName) {
		XStream xstream = new XStream(new DomDriver("utf8"));
		if (!flag) {
			xstream.omitField(c1.getClass(), fieldName);
		}
		xstream.processAnnotations(c1.getClass());
		return xstream.toXML(c1);
	}
}
