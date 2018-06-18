package org.litespring.beans.factory.support;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanFactory;

public class DefaultBeanFactory implements BeanFactory {
	
	public static final String ID_ATTRIBUTE = "id";
	public static final String CLASS_ATTRIBUTE = "class";
	private final Map<String,BeanDefinition> beanDefinitionMap = new ConcurrentHashMap();
	
	/**
	 * 构造文件
	 * @param configFile
	 */
	public DefaultBeanFactory(String configFile) {
		loadBeanDefinition(configFile);
	}

	private void loadBeanDefinition(String configFile) {
		InputStream is = null;
		
		ClassLoader cl = ClassUtils.getDefaultClassLoader();
		is = cl.getResourceAsStream(configFile);
		SAXReader reader = new SAXReader();
		Document doc = reader.read(is);
		
		Element root = doc.getRootElement();  //<beans>
		Iterator<Element> iter = root.elementIterator();
		while(iter.hasNext()){
			Element ele = (Element)iter.next();
			String id = ele.attributeValue(ID_ATTRIBUTE);
			String beanClassName = ele.attributeValue(CLASS_ATTRIBUTE);
			BeanDefinition bd = new GenericBeanDefinition(id,beanClassName);
			this.beanDefinitionMap.put(id,bd);
		}
		
		//视频12min
	}

	public BeanDefinition getBeanDefinition(String beanID) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getBean(String string) {
		// TODO Auto-generated method stub
		return null;
	}

}
