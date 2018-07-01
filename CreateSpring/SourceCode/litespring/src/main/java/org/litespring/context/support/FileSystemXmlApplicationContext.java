package org.litespring.context.support;

import org.litespring.bean.factory.xml.XmlBeanDefinitionReader;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.context.ApplicationContext;
import org.litespring.core.io.FileSystemResource;
import org.litespring.core.io.Resource;

public class FileSystemXmlApplicationContext implements ApplicationContext {

	private DefaultBeanFactory factory = null;
	
	public FileSystemXmlApplicationContext(String configFile){
		factory = new DefaultBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
		Resource resource = new FileSystemResource(configFile);
		reader.loadBeanDefinition(resource);
	}
	
	public Object getBean(String beanID) {
		return factory.getBean(beanID);
	}

}
