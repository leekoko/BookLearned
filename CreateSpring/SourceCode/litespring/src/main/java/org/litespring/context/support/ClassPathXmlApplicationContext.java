package org.litespring.context.support;

import org.litespring.bean.factory.xml.XmlBeanDefinitionReader;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.context.ApplicationContext;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.Resource;

public class ClassPathXmlApplicationContext implements ApplicationContext {

	private DefaultBeanFactory factory = null;
	
	public ClassPathXmlApplicationContext(String configFile){
		factory = new DefaultBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
		Resource resource = new ClassPathResource(configFile);
		reader.loadBeanDefinition(resource);
	}
	
	public Object getBean(String beanID) {
		return factory.getBean(beanID);
	}

}
