package com.store.utils;

import java.io.InputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 *   工厂类
 *   通过接收一个字符串，创建在xml配置文件中找到对应的类并返回
 * @author zhujunwei
 * 2019年3月16日 下午10:17:56
 */
public class BeanFactory {
	
	//解析xml
	//通过传递过来的name获取application.xml中name对应的class值
	public static Object createObject(String name) {
		
		//获取到Document对象
		SAXReader reader = new SAXReader();
		InputStream is = BeanFactory.class.getClassLoader().getResourceAsStream("application.xml");
		try {
			Document document = reader.read(is);
			//通过Document对象获取根节点beans
			Element rootElement = document.getRootElement();
			List<Element> elements = rootElement.elements();
			for (Element element : elements) {
				//取得每一个bean节点
				//获取节点的id属性值
				String id = element.attributeValue("id");
				if(id.equals(name)) {
					//找到对应的类，创建并返回
					String str = element.attributeValue("class");
					Class<?> cla = Class.forName(str);
					return cla.newInstance();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
